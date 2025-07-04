package com.Aryan.chatfinal.controller;

import com.Aryan.chatfinal.model.PrivateMessage;
import com.Aryan.chatfinal.repository.PrivateMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class PrivateMessageController {

    @Autowired
    private PrivateMessageRepository privateMessageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // ✅ Load private chat page with user info
    @GetMapping("/private-chat/{username}")
    public String privateChat(@PathVariable String username, Model model, Principal principal) {
        model.addAttribute("otherUser", username);
        model.addAttribute("currentUser", principal != null ? principal.getName() : "Anonymous");
        return "private-chat";
    }

    // ✅ REST endpoint: Save message via fetch/AJAX
    @PostMapping("/api/private/send")
    @ResponseBody
    public PrivateMessage sendMessage(@RequestBody PrivateMessage message) {
        return privateMessageRepository.save(message);
    }

    // ✅ REST endpoint: Load message history
    @GetMapping("/api/private/messages")
    @ResponseBody
    public List<PrivateMessage> getMessages(
            @RequestParam String sender,
            @RequestParam String receiver) {
        return privateMessageRepository
                .findBySenderUsernameAndReceiverUsernameOrReceiverUsernameAndSenderUsername(
                        sender, receiver, sender, receiver);
    }

    // ✅ WebSocket: Real-time messaging with STOMP
    @MessageMapping("/private-message")
    public void handlePrivateMessage(PrivateMessage message, Principal principal) {
        // Save message
        PrivateMessage saved = privateMessageRepository.save(message);

        // Send to receiver
        messagingTemplate.convertAndSendToUser(
                message.getReceiverUsername(), "/queue/messages", saved);

        // Also send to sender (real-time feedback)
        if (principal != null && !principal.getName().equals(message.getReceiverUsername())) {
            messagingTemplate.convertAndSendToUser(
                    principal.getName(), "/queue/messages", saved);
        }
    }
    @MessageMapping("/typing")
public void handleTyping(PrivateMessage typingInfo, Principal principal) {
    String typingMessage = principal.getName() + " is typing...";
    messagingTemplate.convertAndSendToUser(
        typingInfo.getReceiverUsername(),
        "/queue/typing",
        typingMessage
    );
}

}
