package com.chatapp.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a private chat message between two users.
 * Demonstrates encapsulation and immutability for thread-safety.
 */
public class PrivateMessage {
    private final String messageId;
    private final String senderId;
    private final String senderUsername;
    private final String recipientId;
    private final String recipientUsername;
    private final String content;
    private final LocalDateTime timestamp;

    /**
     * Constructor with validation
     * @param senderId The ID of the user sending the message
     * @param senderUsername The username of the sender
     * @param recipientId The ID of the user receiving the message
     * @param recipientUsername The username of the recipient
     * @param content The message content
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public PrivateMessage(String senderId, String senderUsername, String recipientId, String recipientUsername, String content) {
        validateParameters(senderId, senderUsername, recipientId, recipientUsername, content);
        
        this.messageId = UUID.randomUUID().toString();
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.recipientId = recipientId;
        this.recipientUsername = recipientUsername;
        this.content = content.trim();
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Validates private message parameters
     * @param senderId The sender ID
     * @param senderUsername The sender username
     * @param recipientId The recipient ID
     * @param recipientUsername The recipient username
     * @param content The message content
     * @throws IllegalArgumentException if any parameter is invalid
     */
    private void validateParameters(String senderId, String senderUsername, String recipientId, String recipientUsername, String content) {
        if (senderId == null || senderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Sender ID cannot be null or empty");
        }
        if (senderUsername == null || senderUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Sender username cannot be null or empty");
        }
        if (recipientId == null || recipientId.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipient ID cannot be null or empty");
        }
        if (recipientUsername == null || recipientUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipient username cannot be null or empty");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Private message content cannot be null or empty");
        }
        if (content.trim().length() > 1000) {
            throw new IllegalArgumentException("Private message content cannot exceed 1000 characters");
        }
        if (senderId.equals(recipientId)) {
            throw new IllegalArgumentException("Cannot send private message to self");
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

    public String getRecipientId() {
        return recipientId;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Formats the private message for display
     * @return Formatted private message string
     */
    public String getFormattedMessage() {
        return String.format("[PRIVATE from %s to %s]: %s", senderUsername, recipientUsername, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivateMessage that = (PrivateMessage) o;
        return Objects.equals(messageId, that.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId);
    }

    @Override
    public String toString() {
        return "PrivateMessage{" +
                "messageId='" + messageId + '\'' +
                ", senderUsername='" + senderUsername + '\'' +
                ", recipientUsername='" + recipientUsername + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
