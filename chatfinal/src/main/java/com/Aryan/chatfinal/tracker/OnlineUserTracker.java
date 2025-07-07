package com.Aryan.chatfinal.tracker;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OnlineUserTracker {
    private static final Set<String> onlineUsers = Collections.synchronizedSet(new HashSet<>());

    public static void addUser(String username) {
        onlineUsers.add(username);
    }

    public static void removeUser(String username) {
        onlineUsers.remove(username);
    }

    public static Set<String> getOnlineUsers() {
        return onlineUsers;
    }
}

