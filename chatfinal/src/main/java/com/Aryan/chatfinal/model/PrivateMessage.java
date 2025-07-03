package com.Aryan.chatfinal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "private_messages")
public class PrivateMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderUsername;
    private String receiverUsername;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime timestamp = LocalDateTime.now();

    // Getters and setters
    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getSenderUsername() {
    return senderUsername;
}

public void setSenderUsername(String senderUsername) {
    this.senderUsername = senderUsername;
}

public String getReceiverUsername() {
    return receiverUsername;
}

public void setReceiverUsername(String receiverUsername) {
    this.receiverUsername = receiverUsername;
}

public String getMessage() {
    return message;
}

public void setMessage(String message) {
    this.message = message;
}

public LocalDateTime getTimestamp() {
    return timestamp;
}

public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
}

}
