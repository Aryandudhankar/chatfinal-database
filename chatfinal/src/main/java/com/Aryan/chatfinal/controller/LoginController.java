package com.Aryan.chatfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.Aryan.chatfinal.model.User;
import com.Aryan.chatfinal.service.UserService;

public class LoginController {
        @Autowired
    private UserService userService;


    // Show the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup"; // register.html in src/main/resources/templates/
    }

    // Handle form submission and registration logic
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Check if the username already exists
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "signup";
        }
        // Save the new user
        userService.saveUser(user);
        return "redirect:/login";
    }
}
