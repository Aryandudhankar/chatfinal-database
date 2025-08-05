package com.Aryan.chatfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Aryan.chatfinal.service.EmailService;
@Controller
public class Testemail {
    @Autowired
private EmailService emailService;

@GetMapping("/test-email")

@ResponseBody
public String testEmail() {
    try {
        emailService.sendEmail(
            "Aryandudhankar1@gmail.com", 
            "Test Email from Spring Boot",
            "This is a test email to check SMTP settings."
        );
        return "✅ Email sent successfully!";
    } catch (Exception e) {
        e.printStackTrace();
        return "❌ Failed to send email: " + e.getMessage();
    }
}

}
