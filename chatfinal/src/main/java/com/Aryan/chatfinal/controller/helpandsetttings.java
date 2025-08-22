package com.Aryan.chatfinal.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class helpandsetttings {

    @GetMapping("/help")
    public String helpPage() {
        return "help"; // looks for help.html in templates
    }

    @GetMapping("/settings")
    public String settingsPage() {
        return "settings"; // looks for settings.html in templates
    }
}
