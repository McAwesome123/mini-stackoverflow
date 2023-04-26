package com.example.notifications;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic chatMessage() {
        return TopicBuilder.name("chat-message")
                .build();
    }

    @Bean
    public NewTopic questionAnswer() {
        return TopicBuilder.name("question-answer")
                .build();
    }

    @Bean
    public NewTopic finalAnswer() {
        return TopicBuilder.name("final-answer")
                .build();
    }
}
