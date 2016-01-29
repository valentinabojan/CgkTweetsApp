package com.tweets.service.entity;

import org.springframework.data.annotation.Id;

public class Session {

    @Id
    private String sessionID;

    private String username;

    public Session(String sessionID, String username) {
        this.sessionID = sessionID;
        this.username = username;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getUsername() {
        return username;
    }
}
