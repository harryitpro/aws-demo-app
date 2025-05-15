package com.demo.controlleradvice;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final String type;
    private final int status;
    private final String message;
    private final String path;

    public ErrorResponse(String type, int status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.status = status;
        this.message = message;
        this.path = path;
    }

    // Getters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
