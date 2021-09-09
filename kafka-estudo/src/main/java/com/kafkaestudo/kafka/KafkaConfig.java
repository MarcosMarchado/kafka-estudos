package com.kafkaestudo.kafka;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, Object> producerFactory(){
        return new DefaultKafkaProducerFactory<>(Map.of(
                KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                RETRIES_CONFIG, 0,
                BUFFER_MEMORY_CONFIG, 2000000 //20mb
        ));
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
