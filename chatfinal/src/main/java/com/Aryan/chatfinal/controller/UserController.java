package com.Aryan.chatfinal.controller;

import com.Aryan.chatfinal.model.User;
import com.Aryan.chatfinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Aryan.chatfinal.repository.PrivateMessageRepository;


import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;
// This controller provides an endpoint to search for users by username.
    @GetMapping("/search-users")
    public List<User> searchUsers(@RequestParam("query") String query) {
        return userRepository.findByUsernameContainingIgnoreCase(query);
    }

}
