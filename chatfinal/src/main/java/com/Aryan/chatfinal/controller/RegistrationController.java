package com.Aryan.chatfinal.controller;

import com.Aryan.chatfinal.kafka.LoginEventProducer;
import com.Aryan.chatfinal.model.User;
import com.Aryan.chatfinal.repository.UserRepository;
import com.Aryan.chatfinal.service.AuthService;
import com.Aryan.chatfinal.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginEventProducer loginEventProducer;

    // In-memory tracker (can later be Redis or DB)
    private ConcurrentHashMap<String, Integer> failedAttempts = new ConcurrentHashMap<>();

    // Show the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Handle form submission and registration logic
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "signup";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }

    // Display login form
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("msg", "You have been logged out successfully.");
        }
        return "login";
    }

}
