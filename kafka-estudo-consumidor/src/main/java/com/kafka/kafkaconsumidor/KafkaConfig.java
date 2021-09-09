package com.kafka.kafkaconsumidor;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, Cartao> consumerFactory(){
        StringDeserializer stringDeserializer = new StringDeserializer();
        JsonDeserializer<Cartao> jsonDeserializer = new JsonDeserializer<>(Cartao.class, false);

        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), stringDeserializer, jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Cartao> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Cartao> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    public Map<String, Object> consumerConfigurations(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(GROUP_ID_CONFIG, "consumidor-cartoes"); /*Identificador da máquina consumidora*/
        properties.put(ENABLE_AUTO_COMMIT_CONFIG, true);
        properties.put(SESSION_TIMEOUT_MS_CONFIG, 10000);
        return properties;
    }

}
