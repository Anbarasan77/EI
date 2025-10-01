package com.chatapp.model;

import com.chatapp.exception.ChatRoomException;
import com.chatapp.observer.ChatObserver;
import com.chatapp.observer.ChatSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a chat room in the application. Implements the Observer Pattern as
 * the Subject. Demonstrates encapsulation, thread-safety, and proper state
 * management.
 */
public class ChatRoom implements ChatSubject {

    private static final Logger logger = LoggerFactory.getLogger(ChatRoom.class);
    private static final int MAX_USERS = 100;
    private static final int MAX_MESSAGES = 1000;

    private final String roomId;
    private final String roomName;
    private final LocalDateTime createdAt;
    private final Map<String, User> activeUsers; // All users who have ever joined this room
    private final Set<String> presentUserIds; // Users currently "in" the room interface
    private final List<Message> messageHistory;
    private final List<ChatObserver> observers;
    private Message lastMessage;

    /**
     * Constructor with validation
     *
     * @param roomId The unique identifier for the room
     * @param roomName The name of the room
     * @throws IllegalArgumentException if parameters are invalid
     */
    public ChatRoom(String roomId, String roomName) {
        validateRoomParameters(roomId, roomName);

        this.roomId = roomId;
        this.roomName = roomName.trim();
        this.createdAt = LocalDateTime.now();
        this.activeUsers = new ConcurrentHashMap<>();
        this.presentUserIds = Collections.newSetFromMap(new ConcurrentHashMap<>()); // Thread-safe set
        this.messageHistory = new CopyOnWriteArrayList<>();
        this.observers = new CopyOnWriteArrayList<>();

        logger.info("Chat room created: {} (ID: {})", roomName, roomId);
    }

    /**
     * Validates room parameters
     *
     * @param roomId The room ID
     * @param roomName The room name
     * @throws IllegalArgumentException if parameters are invalid
     */
    private void validateRoomParameters(String roomId, String roomName) {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be null or empty");
        }
        if (roomName == null || roomName.trim().isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be null or empty");
        }
        if (roomName.trim().length() < 3) {
            throw new IllegalArgumentException("Room name must be at least 3 characters long");
        }
        if (roomName.trim().length() > 100) {
            throw new IllegalArgumentException("Room name cannot exceed 100 characters");
        }
    }

    /**
     * Adds a user to the chat room
     *
     * @param user The user to add
     * @throws ChatRoomException if the room is full or user already exists
     */
    public synchronized void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (activeUsers.size() >= MAX_USERS) {
            logger.warn("Failed to add user {} to room {}: Room is full", user.getUsername(), roomId);
            throw new ChatRoomException("Chat room is full. Maximum capacity: " + MAX_USERS);
        }

        if (activeUsers.containsKey(user.getUserId())) {
            logger.warn("User {} already exists in room {}", user.getUsername(), roomId);
            throw new ChatRoomException("User already exists in this chat room");
        }

        activeUsers.put(user.getUserId(), user);
        presentUserIds.add(user.getUserId()); // Mark user as present in the room
        logger.info("User {} joined room {} (Total users: {})", user.getUsername(), roomId, presentUserIds.size());

        notifyUserJoined(user);
    }

    /**
     * Removes a user from the chat room
     *
     * @param userId The ID of the user to remove
     */
    public synchronized void removeUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        User user = activeUsers.get(userId); // Get user, but don't remove from activeUsers map
        if (user != null) {
            presentUserIds.remove(userId); // Mark user as no longer present in the room
            logger.info("User {} left room {} (Remaining users: {})", user.getUsername(), roomId, presentUserIds.size());
            notifyUserLeft(user);
        }
    }

    /**
     * Posts a message to the chat room
     *
     * @param message The message to post
     * @throws ChatRoomException if message is invalid or limit reached
     */
    public synchronized void postMessage(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        if (!message.getRoomId().equals(roomId)) {
            throw new ChatRoomException("Message does not belong to this room");
        }

        if (!activeUsers.containsKey(message.getSenderId())) {
            throw new ChatRoomException("Sender is not a member of this room");
        }

        if (messageHistory.size() >= MAX_MESSAGES) {
            // Remove oldest message to maintain limit
            messageHistory.remove(0);
            logger.debug("Message limit reached. Removed oldest message from room {}", roomId);
        }

        messageHistory.add(message);
        lastMessage = message;

        logger.debug("Message posted in room {} by {}: {}", roomId, message.getSenderUsername(), message.getContent());

        notifyMessageReceived(message);
    }

    @Override
    public void registerObserver(ChatObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null");
        }

        if (!observers.contains(observer)) {
            observers.add(observer);
            logger.debug("Observer registered for room {}", roomId);
        }
    }

    @Override
    public void removeObserver(ChatObserver observer) {
        if (observer != null) {
            observers.remove(observer);
            // Do NOT remove from presentUserIds here. This is handled by ChatRoom.removeUser()
            logger.debug("Observer removed from room {}", roomId);
        }
    }

    @Override
    public void notifyObservers() {
        // This is called by specific notification methods
    }

    /**
     * Notifies observers of a new message
     *
     * @param message The new message
     */
    private void notifyMessageReceived(Message message) {
        for (ChatObserver observer : observers) {
            try {
                // Don't notify the sender
                if (!observer.getObserverUserId().equals(message.getSenderId())) {
                    observer.onMessageReceived(message);
                }
            } catch (Exception e) {
                logger.error("Error notifying observer of message: {}", e.getMessage(), e);
                observer.onError("Failed to receive message notification");
            }
        }
    }

    /**
     * Notifies observers of a user joining
     *
     * @param user The user who joined
     */
    private void notifyUserJoined(User user) {
        for (ChatObserver observer : observers) {
            try {
                if (!observer.getObserverUserId().equals(user.getUserId())) {
                    observer.onUserJoined(user);
                }
            } catch (Exception e) {
                logger.error("Error notifying observer of user join: {}", e.getMessage(), e);
                observer.onError("Failed to receive user join notification");
            }
        }
    }

    /**
     * Notifies observers of a user leaving
     *
     * @param user The user who left
     */
    private void notifyUserLeft(User user) {
        for (ChatObserver observer : observers) {
            try {
                if (!observer.getObserverUserId().equals(user.getUserId())) {
                    observer.onUserLeft(user);
                }
            } catch (Exception e) {
                logger.error("Error notifying observer of user leave: {}", e.getMessage(), e);
                observer.onError("Failed to receive user leave notification");
            }
        }
    }

    /**
     * Gets a copy of active users list
     *
     * @return List of active users
     */
    public List<User> getActiveUsers() {
        List<User> currentlyActive = new ArrayList<>();
        for (String userId : presentUserIds) {
            User user = activeUsers.get(userId);
            if (user != null && user.isActive()) { // Ensure user exists and is globally active
                currentlyActive.add(user);
            }
        }
        return currentlyActive;
    }

    /**
     * Gets a copy of message history
     *
     * @return List of messages
     */
    public List<Message> getMessageHistory() {
        return new ArrayList<>(messageHistory);
    }

    /**
     * Gets a user by ID
     *
     * @param userId The user ID
     * @return The user or null if not found
     */
    public User getUser(String userId) {
        return activeUsers.get(userId);
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getUserCount() {
        return presentUserIds.size();
    }

    public int getMessageCount() {
        return messageHistory.size();
    }

    @Override
    public String toString() {
        return "ChatRoom{"
                + "roomId='" + roomId + '\''
                + ", roomName='" + roomName + '\''
                + ", activeUsers=" + activeUsers.size()
                + ", messages=" + messageHistory.size()
                + '}';
    }
}
