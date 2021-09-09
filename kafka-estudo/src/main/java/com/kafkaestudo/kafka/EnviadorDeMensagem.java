package com.kafkaestudo.kafka;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import com.github.javafaker.Finance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class EnviadorDeMensagem {

    private final Logger logger;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EnviadorDeMensagem(KafkaTemplate<String, Object> kafkaTemplate) {
        this.logger = LoggerFactory.getLogger(EnviadorDeMensagem.class);
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void enviaMensagem() {

        Faker faker = new Faker();
        Finance finance = faker.finance();

        Cartao cartao = new Cartao(
                finance.creditCard(),
                faker.name().fullName(),
                CreditCardType.MASTERCARD.name(),
                new Random().nextInt(999));

                            /*1- TÓPICO,               2- KEY,             3- MENSAGEM*/
        /*kafkaTemplate.send("NOVO_CARTAO", CreditCardType.MASTERCARD.name() ,cartao);*/
        kafkaTemplate.send("NOVO_CARTAO", cartao).addCallback(
                success -> logger.info("Informações enviadas {}", success.getProducerRecord().value()),
                failure -> logger.info("Deu error")
        );
    }

}
