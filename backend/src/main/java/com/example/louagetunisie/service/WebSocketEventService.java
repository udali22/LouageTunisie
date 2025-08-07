package com.example.louagetunisie.service;

import com.example.louagetunisie.model.WebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketEventService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketEventService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Envoie un message WebSocket à un topic avec une action (create/update/delete).
     * @param topic le topic (ex: "clients", "chauffeurs", etc.)
     * @param action le type d'action (create, update, delete, etc.)
     * @param payload les données
     */
    public void sendEvent(String topic, String action, Object payload) {
        messagingTemplate.convertAndSend("/topic/" + topic, new WebSocketMessage(action, payload));
    }
}

