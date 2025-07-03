package com.Aryan.chatfinal.controller;

import com.Aryan.chatfinal.model.PrivateMessage;
import com.Aryan.chatfinal.repository.PrivateMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

import java.util.List;
@Controller
public class PrivateMessageController {

    @Autowired
    private PrivateMessageRepository privateMessageRepository;

   @GetMapping("/private-chat/{username}")
public String privateChat(@PathVariable String username, Model model, Principal principal) {
    model.addAttribute("otherUser", username);
    model.addAttribute("currentUser", principal != null ? principal.getName() : "Anonymous");
    return "private-chat";
}

    // ✅ For sending messages (AJAX / JS)
    @PostMapping("/api/private/send")
    @ResponseBody
    public PrivateMessage sendMessage(@RequestBody PrivateMessage message) {
        return privateMessageRepository.save(message);
    }

    // ✅ For fetching messages
    @GetMapping("/api/private/messages")
    @ResponseBody
    public List<PrivateMessage> getMessages(
            @RequestParam String sender,
            @RequestParam String receiver) {
        return privateMessageRepository
                .findBySenderUsernameAndReceiverUsernameOrReceiverUsernameAndSenderUsername(
                        sender, receiver, sender, receiver);
    }


}


