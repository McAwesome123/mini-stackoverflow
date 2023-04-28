package com.example.chat;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Predicate;

@RestController
public class ChatController {
    @Autowired
    private CassandraOperations cassandraTemplate;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/chat/{id}")
    public ResponseEntity<String> sendMessage(@PathVariable Long recipientId, @RequestParam("message") String message, HttpSession session) {
        String senderId = session.getAttribute("userId").toString();

        ChatMessage msg = new ChatMessage(Long.getLong(senderId), recipientId, message);
        cassandraTemplate.insert(msg);
        kafkaTemplate.send("chat-message", new Msg(recipientId, message).toString());
        return ResponseEntity.ok().body("OK");
    }

    @GetMapping("/chat/{id}")
    public ResponseEntity<String> getChat(@PathVariable Long userId, HttpSession session) {
        Predicate<ChatMessage> chatPredicate = msg -> msg.getRecipientId() == userId && msg.getSenderId() == Long.getLong(session.getAttribute("userId").toString());
        List<ChatMessage> messages = (List<ChatMessage>) cassandraTemplate.query(ChatMessage.class).stream().filter(chatPredicate);

        return ResponseEntity.ok().body("OK");
    }
}
