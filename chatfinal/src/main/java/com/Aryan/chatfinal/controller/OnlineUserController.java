package com.Aryan.chatfinal.controller;

import com.Aryan.chatfinal.tracker.OnlineUserTracker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class OnlineUserController {

    @GetMapping("/api/online-users")
    public Set<String> getOnlineUsers() {
        return OnlineUserTracker.getOnlineUsers();
    }
}
