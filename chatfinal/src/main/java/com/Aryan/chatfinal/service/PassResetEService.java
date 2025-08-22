
// package com.Aryan.chatfinal.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;

// @Service
// public class PassResetEService {

//     @Autowired
//     private JavaMailSender mailSender;

//     public void sendPasswordResetEmail(String to, String username, String token) {
//         String subject = "Password Reset Request";
//         String resetLink = "https://your-domain.com/reset-password?token=" + token;

//         String text = "Hello " + username + ",\n\n" +
//                       "Click the following link to reset your password:\n" + resetLink + "\n\n" +
//                       "If you didn't request this, please ignore this email.";

//         SimpleMailMessage message = new SimpleMailMessage();
//         message.setTo(to);
//         message.setSubject(subject);
//         message.setText(text);

//         mailSender.send(message);
//     }
// }
