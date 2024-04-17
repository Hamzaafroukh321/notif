package com.example.managementsystem.models;

import java.time.LocalDateTime;

public class Notification {
    private String message;
    private LocalDateTime timestamp;
    private String recipient;


    // Getters et setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}