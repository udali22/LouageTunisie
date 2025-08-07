package com.example.louagetunisie.model;

public class WebSocketMessage {
    private String type; // ex: "create", "update", "delete"
    private Object data;

    public WebSocketMessage() {
    }

    public WebSocketMessage(String type, Object data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

