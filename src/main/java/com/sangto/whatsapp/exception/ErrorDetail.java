package com.sangto.whatsapp.exception;

import java.time.LocalDateTime;

public class ErrorDetail {

    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorDetail() {
    }

    public ErrorDetail(String error, String message, LocalDateTime timestamp) {
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    @Override
    public String toString() {
        return "ErrorDetail [error=" + error + ", message=" + message + ", timestamp=" + timestamp + "]";
    }
}
