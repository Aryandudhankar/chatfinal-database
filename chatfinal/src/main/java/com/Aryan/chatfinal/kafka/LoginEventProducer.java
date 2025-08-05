package com.Aryan.chatfinal.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginEventProducer {

    private static final String TOPIC = "login-failures";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendLoginFailureMessage(String username) {
           System.out.println(">>> Producing Kafka message for username: " + username);
    kafkaTemplate.send(TOPIC, username)
        .whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("!!! Failed to send message: " + ex.getMessage());
            } else {
                System.out.println(">>> Kafka send success: " + result.getRecordMetadata());
            }
        });
    }
}
