package com.Aryan.chatfinal;

import java.time.LocalTime;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


import org.springframework.messaging.simp.SimpMessagingTemplate;


@Controller
public class ChatController {
// This class handles the chat messages and bot queries
private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


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
public void addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    // Store username in session
    headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

    // User joined message
    ChatMessage joinMessage = new ChatMessage();
    joinMessage.setSender(chatMessage.getSender());
    joinMessage.setContent(chatMessage.getSender() + " joined the chat.");
    joinMessage.setType(MessageType.JOIN);
    joinMessage.setTime(LocalTime.now().toString());
    messagingTemplate.convertAndSend("/topic/public", joinMessage);

    // Bot welcome message
    ChatMessage botMessage = new ChatMessage();
    botMessage.setSender("Bot 🤖");
    botMessage.setContent("👋 Welcome! "+ chatMessage.getSender() +"Try typing '@bot help' for All bot commands .");
    botMessage.setType(MessageType.CHAT);
    botMessage.setTime(LocalTime.now().toString());
    messagingTemplate.convertAndSend("/topic/public", botMessage);
}

 

// This method handels the bot query logic 

    private String handleBotQuery(String query) {
    query = query.toLowerCase();

    if (query.contains("joke")) {
        return "Why did the programmer quit his job? Because he didn't get arrays. 😂";
    } else if (query.contains("time")) {
        return "Current server time is: " + LocalTime.now().withNano(0);
    } else if (query.contains("help")) {
        return "Try '@bot hello','@bot tell me a joke', '@bot what time is it','@bot help','@bot Who created you?','@bot sing' or '@bot tell me a fact'";
    } else if (query.contains("hello")) {
        return "Hello! How are you doing today?";
    } else if(query.contains("sing")){
        return "🎶 Twinkle, twinkle, little bot,\n" +
               "How I wonder what you’ve got!\n" +
               "Up above the world so high,\n" +
               "Like a server in the sky! 🎶";
      }
      else if(query.contains("created")){
        return "I was created by Great Sir Aryan dudhankar!!.....Lord Save the King 👑";
      }else if(query.contains("fact")){
        return "You technically start dying the moment you’re born.\n" + //
                        "Dark? Yes. Existential? Also yes. Happy thoughts. 🌱☠️";
      }
    else {
        return "I'm just a simple bot 🤖. Try typing '@bot help' for options!";
    }
 }
}

