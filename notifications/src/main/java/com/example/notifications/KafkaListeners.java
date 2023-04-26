package com.example.notifications;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(topics = "chat-message", groupId = "chat1")
    void listener(String data) {
        System.out.println("Listener received: " + data);
    }
}
