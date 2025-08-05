package com.Aryan.chatfinal.kafka;

import com.Aryan.chatfinal.model.User;
import com.Aryan.chatfinal.repository.UserRepository;
import com.Aryan.chatfinal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class EmailConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "login-failures", groupId = "login-fail-group-test")
    public void handleLoginFailure(String username) {
        logger.info("Received Kafka message: login failure for username '{}'", username);

        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String toEmail = user.getEmail();
            String subject = "Alert: Multiple Failed Login Attempts";
            String body = "We detected multiple failed login attempts on your account. If this wasn’t you, please reset your password or contact support.";

            logger.info("User found. Sending email to: {}", toEmail);
            emailService.sendEmail(toEmail, subject, body);
        } else {
            logger.warn("No user found with username received from Kafka: {}", username);
        }
    }
}
