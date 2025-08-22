// package com.Aryan.chatfinal.kafka;

// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.stereotype.Service;

// @Service
// public class ChangePasswordProducer {
//     private static final String TOPIC = "change-password-topic";
//     private final KafkaTemplate<String, ChangePasswordEvent> kafkaTemplate;

//     public ChangePasswordProducer(KafkaTemplate<String, ChangePasswordEvent> kafkaTemplate) {
//         this.kafkaTemplate = kafkaTemplate;
//     }

//     public void sendChangePasswordEvent(ChangePasswordEvent event) {
//         kafkaTemplate.send(TOPIC, event);
//     }
// }
