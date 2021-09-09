package com.kafka.kafkaconsumidor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LeitorDeMensagem {

    private final Logger logger;

    public LeitorDeMensagem() {
        this.logger = LoggerFactory.getLogger(LeitorDeMensagem.class);
    }

    @KafkaListener(topics = "NOVO_CARTAO", groupId = "consumidorDeMensagens", containerFactory = "kafkaListenerContainerFactory")
    public void consomeMennsagem(ConsumerRecord<String, Cartao> record){
        logger.info("Consumindo mensagem {}", record.value().toString());
    }


}
