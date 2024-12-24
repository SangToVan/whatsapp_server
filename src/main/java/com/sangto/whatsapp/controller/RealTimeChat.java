package com.sangto.whatsapp.controller;

import com.sangto.whatsapp.modal.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class RealTimeChat {

    private SimpMessagingTemplate template;

    @MessageMapping("/message")
    @SendTo("/group/public")
    public Message receiveMessage(@Payload Message message) {

        template.convertAndSend("/group/" + message.getChat().getId().toString(), message);

        return message;
    }
}
