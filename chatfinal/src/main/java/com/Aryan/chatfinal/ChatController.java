package com.Aryan.chatfinal;

import java.time.LocalTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  // this method handels the normal sending of messages and the @bot queries
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        String content = chatMessage.getContent().trim();

        // 🤖 Bot response logic
        if (content.toLowerCase().startsWith("@bot")) {
            String query = content.substring(4).trim();
            String botReply = handleBotQuery(query);

            ChatMessage botResponse = new ChatMessage();
            botResponse.setSender("Bot 🤖");
            botResponse.setContent(botReply);
            botResponse.setType(MessageType.CHAT);
            botResponse.setTime(LocalTime.now().toString());
            return botResponse;
        }

        // Normal user message
        chatMessage.setTime(LocalTime.now().toString());
        return chatMessage;
    }

// This method handels the addition  of  a new  user to the chat 

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Save username to WebSocket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        chatMessage.setContent(chatMessage.getSender() + " joined the chat.");
        chatMessage.setType(MessageType.JOIN);
        chatMessage.setTime(LocalTime.now().toString());
        return chatMessage;
    }



// This method handels the bot query logic 

    private String handleBotQuery(String query) {
    query = query.toLowerCase();

    if (query.contains("joke")) {
        return "Why did the programmer quit his job? Because he didn't get arrays. 😂";
    } else if (query.contains("time")) {
        return "Current server time is: " + LocalTime.now().withNano(0);
    } else if (query.contains("help")) {
        return "Try '@bot tell me a joke', '@bot what time is it', or '@bot help'.";
    } else if (query.contains("hello")) {
        return "Hello! How are you doing today?";
    } else {
        return "I'm just a simple bot 🤖. Try typing '@bot help' for options!";
    }
}
}
