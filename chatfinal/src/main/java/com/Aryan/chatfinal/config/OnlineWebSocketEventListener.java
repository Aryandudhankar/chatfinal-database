package com.Aryan.chatfinal.config;

import com.Aryan.chatfinal.tracker.OnlineUserTracker;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class OnlineWebSocketEventListener {

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Authentication auth = (Authentication) accessor.getUser();

        if (auth != null) {
            String username = auth.getName();
            OnlineUserTracker.addUser(username);
            System.out.println("Connected: " + username);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Authentication auth = (Authentication) accessor.getUser();

        if (auth != null) {
            String username = auth.getName();
            OnlineUserTracker.removeUser(username);
            System.out.println("Disconnected: " + username);
        }
    }
}
