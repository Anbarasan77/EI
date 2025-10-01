package com.chatapp.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a chat message in the application.
 * Demonstrates encapsulation and immutability for thread-safety.
 */
public class Message {
    private final String messageId;
    private final String senderId;
    private final String senderUsername;
    private final String content;
    private final LocalDateTime timestamp;
    private final String roomId;

    /**
     * Constructor with validation
     * @param senderId The ID of the user sending the message
     * @param senderUsername The username of the sender
     * @param content The message content
     * @param roomId The ID of the chat room
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Message(String senderId, String senderUsername, String content, String roomId) {
        validateParameters(senderId, senderUsername, content, roomId);
        
        this.messageId = UUID.randomUUID().toString();
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.content = content.trim();
        this.timestamp = LocalDateTime.now();
        this.roomId = roomId;
    }

    /**
     * Validates message parameters
     * @param senderId The sender ID
     * @param senderUsername The sender username
     * @param content The message content
     * @param roomId The room ID
     * @throws IllegalArgumentException if any parameter is invalid
     */
    private void validateParameters(String senderId, String senderUsername, String content, String roomId) {
        if (senderId == null || senderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Sender ID cannot be null or empty");
        }
        if (senderUsername == null || senderUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Sender username cannot be null or empty");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty");
        }
        if (content.trim().length() > 1000) {
            throw new IllegalArgumentException("Message content cannot exceed 1000 characters");
        }
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be null or empty");
        }
    }

    public String getMessageId() {
        return messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getRoomId() {
        return roomId;
    }

    /**
     * Formats the message for display
     * @return Formatted message string
     */
    public String getFormattedMessage() {
        return String.format("[%s]: %s", senderUsername, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(messageId, message.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", senderUsername='" + senderUsername + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
