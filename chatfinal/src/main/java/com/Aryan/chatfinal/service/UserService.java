package com.Aryan.chatfinal.service;

import com.Aryan.chatfinal.model.User;
import com.Aryan.chatfinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Cache evicted when new user is saved or updated
    @CacheEvict(value = "userCache", key = "#user.username")
    public void saveUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword())); // Saves encoded password
    userRepository.save(user); // Saves user with username, email, and encoded password
}
// Cache hit if username already looked up
    @Cacheable(value = "userCache", key = "#username")
    
    public User findByUsername(String username) {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    return optionalUser.orElse(null); // or throw an exception if preferred
}
}
