// package com.Aryan.chatfinal.config;

// import com.Aryan.chatfinal.kafka.ChangePasswordEvent;
// import org.apache.kafka.clients.producer.ProducerConfig;
// import org.apache.kafka.common.serialization.StringSerializer;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.core.DefaultKafkaProducerFactory;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.kafka.core.ProducerFactory;
// import org.springframework.kafka.support.serializer.JsonSerializer;

// import java.util.HashMap;
// import java.util.Map;

// @Configuration
// public class KafkaProducerConfig {

//     @Bean
//     public ProducerFactory<String, ChangePasswordEvent> changePasswordProducerFactory() {
//         Map<String, Object> config = new HashMap<>();
//         config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-619z3.us-east1.gcp.confluent.cloud:9092"); 
//         config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//         config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//         return new DefaultKafkaProducerFactory<>(config);
//     }

//     @Bean
//     public KafkaTemplate<String, ChangePasswordEvent> changePasswordKafkaTemplate() {
//         return new KafkaTemplate<>(changePasswordProducerFactory());
//     }
// }


