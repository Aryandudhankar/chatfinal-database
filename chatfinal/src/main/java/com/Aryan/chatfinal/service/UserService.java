package com.Aryan.chatfinal.service;

import com.Aryan.chatfinal.model.User;
import com.Aryan.chatfinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword())); // Saves encoded password
    userRepository.save(user); // Saves user with username, email, and encoded password
}
    public User findByUsername(String username) {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    return optionalUser.orElse(null); // or throw an exception if preferred
}


   public User authenticate(String username, String password) {
    User user = findByUsername(username);
    if (user != null && passwordEncoder.matches(password, user.getPassword())) {
        return user;
    }
    return null;
}

}
