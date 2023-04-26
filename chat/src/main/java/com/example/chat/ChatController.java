package com.example.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/chat")
    public ResponseEntity<String> sendMessage() {
        kafkaTemplate.send("chat-message", new Msg("Chatbot", "Content").toString());
        return ResponseEntity.ok().body("OK");
    }
}
