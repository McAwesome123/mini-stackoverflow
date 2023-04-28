package com.example.chat;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


@Table(value = "user_chat")
public class ChatMessage {
    @PrimaryKeyColumn(
            name = "sender_id",
            ordinal = 0,
            type = PrimaryKeyType.PARTITIONED
    ) private long senderId;

    @PrimaryKeyColumn(
            name = "recipient_id",
            ordinal = 0,
            type = PrimaryKeyType.PARTITIONED
    ) private long recipientId;

    @PrimaryKeyColumn(
            name = "message_date",
            ordinal = 0,
            type = PrimaryKeyType.PARTITIONED
        ) private Date date;

    @Column(value = "message")
    private String message;

    public ChatMessage(long senderId, long recipientId, String message) {

        this.senderId = senderId;
        this.recipientId = recipientId;
        this.message = message;
        this.date = new Date();
    }

    public long getSenderId() {
        return senderId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
