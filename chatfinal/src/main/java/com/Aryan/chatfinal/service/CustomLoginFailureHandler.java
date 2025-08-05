package com.Aryan.chatfinal.service;

import com.Aryan.chatfinal.kafka.LoginEventProducer;
import com.Aryan.chatfinal.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginEventProducer loginEventProducer;

    private ConcurrentHashMap<String, Integer> failedAttempts = new ConcurrentHashMap<>();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String username = request.getParameter("username");
        System.out.println(">>> Login failed for: " + username);

        int attempts = failedAttempts.getOrDefault(username, 0) + 1;
        failedAttempts.put(username, attempts);
        System.out.println(">>> Failed attempts for " + username + ": " + attempts);

        if (attempts >= 3 && userRepository.findByUsername(username).isPresent()) {
            System.out.println(">>> Triggering Kafka alert for user: " + username);
            failedAttempts.put(username, 0); // reset after alert
            loginEventProducer.sendLoginFailureMessage(username);
        } else if (attempts >= 3) {
            System.out.println(">>> Username not found in DB, skipping Kafka send.");
            failedAttempts.put(username, 0);
        }

        // Redirect back to login page with error
        response.sendRedirect("/login?error=true");
    }
}
