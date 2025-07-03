package com.Aryan.chatfinal.repository;

import com.Aryan.chatfinal.model.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {
    List<PrivateMessage> findBySenderUsernameAndReceiverUsernameOrReceiverUsernameAndSenderUsername(
        String sender1, String receiver1, String sender2, String receiver2);
    // This method retrieves messages between two users, regardless of who sent or received them.
        @Query("SELECT DISTINCT p.receiverUsername FROM PrivateMessage p WHERE p.senderUsername = :username " +
       "UNION " +
       "SELECT DISTINCT p.senderUsername FROM PrivateMessage p WHERE p.receiverUsername = :username")
     List<String> findChatPartners(@Param("username") String username);
}
