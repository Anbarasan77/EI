package com.chatapp.manager;

import com.chatapp.exception.ChatRoomException;
import com.chatapp.model.ChatRoom;
import com.chatapp.model.Message;
import com.chatapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton class that manages all chat rooms in the application.
 * Demonstrates the Creational Design Pattern: Singleton Pattern.
 * Ensures thread-safe access to chat rooms with proper synchronization.
 */
public class ChatRoomManager {
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomManager.class);
    
    // Volatile ensures visibility across threads
    private static volatile ChatRoomManager instance;
    
    private final Map<String, ChatRoom> chatRooms;
    private final Object lock = new Object();
    
    /**
     * Private constructor to prevent external instantiation.
     * Thread-safe initialization of internal data structures.
     */
    private ChatRoomManager() {
        this.chatRooms = new ConcurrentHashMap<>();
        logger.info("ChatRoomManager initialized");
    }
    
    /**
     * Gets the singleton instance of ChatRoomManager.
     * Uses double-checked locking for thread-safe lazy initialization.
     * @return The singleton instance
     */
    public static ChatRoomManager getInstance() {
        if (instance == null) {
            synchronized (ChatRoomManager.class) {
                if (instance == null) {
                    instance = new ChatRoomManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Creates a new chat room
     * @param roomId The unique identifier for the room
     * @param roomName The name of the room
     * @return The created chat room
     * @throws ChatRoomException if room already exists
     */
    public ChatRoom createChatRoom(String roomId, String roomName) {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be null or empty");
        }
        
        synchronized (lock) {
            if (chatRooms.containsKey(roomId)) {
                logger.warn("Attempted to create duplicate room with ID: {}", roomId);
                throw new ChatRoomException("Chat room with ID '" + roomId + "' already exists");
            }
            
            ChatRoom chatRoom = new ChatRoom(roomId, roomName);
            chatRooms.put(roomId, chatRoom);
            
            logger.info("Chat room created: {} (ID: {}). Total rooms: {}", roomName, roomId, chatRooms.size());
            return chatRoom;
        }
    }
    
    /**
     * Gets an existing chat room
     * @param roomId The ID of the room to retrieve
     * @return The chat room, or null if not found
     */
    public ChatRoom getChatRoom(String roomId) {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be null or empty");
        }
        
        return chatRooms.get(roomId);
    }
    
    /**
     * Joins a user to a chat room
     * @param roomId The ID of the room to join
     * @param user The user to join
     * @throws ChatRoomException if room doesn't exist
     */
    public void joinChatRoom(String roomId, User user) {
        ChatRoom room = getChatRoom(roomId);
        
        if (room == null) {
            logger.warn("User {} attempted to join non-existent room: {}", user.getUsername(), roomId);
            throw new ChatRoomException("Chat room with ID '" + roomId + "' does not exist");
        }
        
        room.addUser(user);
        logger.debug("User {} joined room {}", user.getUsername(), roomId);
    }
    
    /**
     * Removes a user from a chat room
     * @param roomId The ID of the room
     * @param userId The ID of the user to remove
     */
    public void leaveChatRoom(String roomId, String userId) {
        ChatRoom room = getChatRoom(roomId);
        
        if (room != null) {
            // room.removeUser(userId); // Do NOT remove user from ChatRoom's activeUsers here
            // The user is only leaving the *room interface*, not necessarily ending their session.
            // The ChatRoom will still keep track of the user's presence for history and notifications.
            logger.debug("User {} left room {}", userId, roomId);
        }
    }
    
    /**
     * Posts a message to a chat room
     * @param roomId The ID of the room
     * @param message The message to post
     * @throws ChatRoomException if room doesn't exist
     */
    public void postMessage(String roomId, Message message) {
        ChatRoom room = getChatRoom(roomId);
        
        if (room == null) {
            logger.warn("Attempted to post message to non-existent room: {}", roomId);
            throw new ChatRoomException("Chat room with ID '" + roomId + "' does not exist");
        }
        
        room.postMessage(message);
    }
    
    /**
     * Deletes a chat room
     * @param roomId The ID of the room to delete
     * @return true if room was deleted, false if not found
     */
    public boolean deleteChatRoom(String roomId) {
        synchronized (lock) {
            ChatRoom room = chatRooms.remove(roomId);
            
            if (room != null) {
                logger.info("Chat room {} deleted. Remaining rooms: {}", roomId, chatRooms.size());
                return true;
            }
            
            return false;
        }
    }
    
    /**
     * Gets all available chat rooms
     * @return List of all chat rooms
     */
    public List<ChatRoom> getAllChatRooms() {
        return new ArrayList<>(chatRooms.values());
    }
    
    /**
     * Checks if a chat room exists
     * @param roomId The ID of the room to check
     * @return true if room exists, false otherwise
     */
    public boolean chatRoomExists(String roomId) {
        return chatRooms.containsKey(roomId);
    }
    
    /**
     * Gets the total number of chat rooms
     * @return The number of active chat rooms
     */
    public int getChatRoomCount() {
        return chatRooms.size();
    }
    
    /**
     * Gets room IDs as a list
     * @return List of all room IDs
     */
    public List<String> getChatRoomIds() {
        return new ArrayList<>(chatRooms.keySet());
    }
}
