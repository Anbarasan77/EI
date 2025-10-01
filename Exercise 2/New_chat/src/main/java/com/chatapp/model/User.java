package com.chatapp.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a user in the chat application.
 * Implements encapsulation by keeping fields private and providing controlled access.
 */
public class User {
    private final String userId;
    private final String username;
    private final LocalDateTime joinedAt;
    private boolean isActive;

    /**
     * Constructor with validation
     * @param username The username for the user
     * @throws IllegalArgumentException if username is null or empty
     */
    public User(String username) {
        validateUsername(username);
        this.userId = UUID.randomUUID().toString();
        this.username = username.trim();
        this.joinedAt = LocalDateTime.now();
        this.isActive = true;
    }

    /**
     * Validates the username
     * @param username The username to validate
     * @throws IllegalArgumentException if username is invalid
     */
    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.trim().length() < 2) {
            throw new IllegalArgumentException("Username must be at least 2 characters long");
        }
        if (username.trim().length() > 50) {
            throw new IllegalArgumentException("Username cannot exceed 50 characters");
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
