package com.Aryan.chatfinal.controller;

import com.Aryan.chatfinal.repository.PrivateMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class ChatHistoryController {

    @Autowired
    private PrivateMessageRepository messageRepository;

    @GetMapping("/history")
    public List<String> getChatHistory(Principal principal) {
        String currentUser = principal.getName();
        return messageRepository.findAll().stream()
                .filter(m -> m.getSenderUsername().equals(currentUser) || m.getReceiverUsername().equals(currentUser))
                .map(m -> m.getSenderUsername().equals(currentUser) ? m.getReceiverUsername() : m.getSenderUsername())
                .distinct()
                .collect(Collectors.toList());
    }
}
