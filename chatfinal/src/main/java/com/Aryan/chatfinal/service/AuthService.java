package com.Aryan.chatfinal.service;

import com.Aryan.chatfinal.model.User;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;



@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired private CacheManager cacheManager;

@PostConstruct
public void logCacheManager() {
    System.out.println("CacheManager class: " + cacheManager.getClass().getName());
}

  //@Cacheable(value = "users", key = "#username")
    public User authenticate(String username, String password) {
        User user = userService.findByUsername(username); // This will now go through proxy and use @Cacheable
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
