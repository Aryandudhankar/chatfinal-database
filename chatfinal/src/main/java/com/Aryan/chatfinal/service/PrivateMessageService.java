package com.Aryan.chatfinal.service;

import com.Aryan.chatfinal.model.PrivateMessage;
import com.Aryan.chatfinal.repository.PrivateMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Service
public class PrivateMessageService {

    private final PrivateMessageRepository repository;

    public PrivateMessageService(PrivateMessageRepository repository) {
        this.repository = repository;
    }
 // Save a new message
    public void saveMessage(PrivateMessage message) {
        repository.save(message);
    }
 // Retrieve chat history between two users
    @Cacheable(value = "chatHistory", key = "T(java.util.Objects).hash(#user1, #user2)")
public List<PrivateMessage> getChatBetween(String user1, String user2) {
    return repository.findBySenderUsernameAndReceiverUsernameOrReceiverUsernameAndSenderUsername(
            user1, user2, user1, user2
    );
}
}
