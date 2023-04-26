package com.example.notifications;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(topics = "chat-message", groupId = "chat1")
    void chat_listener(String data) {
        System.out.println("Listener received: " + data);
    }

    @KafkaListener(topics = "mark-answer", groupId = "chat1")
    void mark_listener(String data) {
        System.out.println("Answer is marked: " + data);
    }

    @KafkaListener(topics = "question-answer", groupId = "chat1")
    void answer_listener(String data) {
        System.out.println("Received answer: " + data);
    }
}
