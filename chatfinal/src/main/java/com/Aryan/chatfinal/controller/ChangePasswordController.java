// package com.Aryan.chatfinal.controller;

// import com.Aryan.chatfinal.kafka.ChangePasswordEvent;
// import com.Aryan.chatfinal.kafka.ChangePasswordProducer;
// import com.Aryan.chatfinal.model.User;
// import com.Aryan.chatfinal.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import java.security.SecureRandom;
// import java.util.Base64;

// @RestController
// @RequestMapping("/auth")
// public class ChangePasswordController {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private ChangePasswordProducer changePasswordProducer;

//     @PostMapping("/forgot-password")
//     public String forgotPassword(@RequestParam String email) {
//         // Find user or throw exception if not found
//         User user = userRepository.findByEmail(email)
//                 .orElseThrow(() -> new RuntimeException("No account found for this email."));

//         // Generate secure reset token
//         String token = generateToken();
//         user.setResetToken(token);
//         userRepository.save(user);

//         // Send event via Kafka
//         ChangePasswordEvent event = new ChangePasswordEvent(email, token);
//         changePasswordProducer.sendChangePasswordEvent(event);

//         return "Password reset link has been sent to your email.";
//     }

//     private String generateToken() {
//         SecureRandom secureRandom = new SecureRandom();
//         byte[] tokenBytes = new byte[24];
//         secureRandom.nextBytes(tokenBytes);
//         return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
//     }
// }
