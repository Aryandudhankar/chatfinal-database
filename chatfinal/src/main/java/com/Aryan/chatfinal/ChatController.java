package com.Aryan.chatfinal;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.net.http.HttpRequest;




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
    return callOpenAI(query);
}

//This method is used to call the openai api to get response from AI


@Value("${openai.api.key}")
private String openAiApiKey;
private String callOpenAI(String query) {
    try {
        HttpClient client = HttpClient.newHttpClient();

        String json = """
        {
          "model": "gpt-3.5-turbo",
          "messages": [
            {"role": "system", "content": "You are a helpful assistant in a chat."},
            {"role": "user", "content": "%s"}
          ]
        }
        """.formatted(query);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/chat/completions"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + openAiApiKey)
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("===== RAW OPENAI RESPONSE =====");
        System.out.println(response.body());


   ObjectMapper mapper = new ObjectMapper();
JsonNode root = mapper.readTree(response.body());

if (!root.has("choices")) {
    return "⚠️ Bot response missing 'choices'.";
}

JsonNode choices = root.get("choices");
if (!choices.isArray() || choices.size() == 0) {
    return "⚠️ Bot response has no choices.";
}

JsonNode messageNode = choices.get(0).path("message").path("content");

if (messageNode.isMissingNode()) {
    return "⚠️ Bot message content not found.";
}
System.out.println("KEY: " + openAiApiKey);

return messageNode.asText().trim();

    } catch (Exception e) {
        e.printStackTrace();
        return "Sorry, I couldn't get a response from the AI.";
    }
}


}

