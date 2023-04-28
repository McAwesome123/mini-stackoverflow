package com.example.notifications;

import jakarta.servlet.http.HttpSession;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(topics = "chat-message", groupId = "1")
    void chat_listener(String data) {
        System.out.println("Message received: " + data);
        // TODO: send email?
    }

    @KafkaListener(topics = "mark-answer", groupId = "1")
    void mark_listener(String data) {
        System.out.println("Answer is marked: " + data);
    }

    @KafkaListener(topics = "question-answer", groupId = "1")
    void answer_listener(String data) {
        System.out.println("Received answer: " + data);
    }
}
