// package com.Aryan.chatfinal.kafka;

// import com.Aryan.chatfinal.service.PassResetEService;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Service;

// @Service
// public class ChangePasswordConsumer {

//     private final PassResetEService passResetEService;
//     private final String appBaseUrl;

//     public ChangePasswordConsumer(
//             PassResetEService passResetEService,
//             @Value("${app.base-url:https://chatfinal-ova1.onrender.com}") String appBaseUrl) {
//         this.passResetEService = passResetEService;
//         this.appBaseUrl = appBaseUrl;
//     }

//     @KafkaListener(topics = "change-password-topic", groupId = "password-group")
//     public void consume(ChangePasswordEvent event) {
//         String resetLink = appBaseUrl + "/password/reset?token=" + event.getToken();

//         String body = "Hi " + event.getUsername() + ",\n\n"
//                 + "Click the link below to reset your password:\n"
//                 + resetLink + "\n\n"
//                 + "If you didn't request this, you can ignore this email.";

//         passResetEService.sendPasswordResetEmail(event.getEmail(), "Password Reset Request", body);
//     }
// }
