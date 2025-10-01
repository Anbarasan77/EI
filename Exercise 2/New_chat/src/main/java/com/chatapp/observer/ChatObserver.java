package com.chatapp.observer;

import com.chatapp.model.Message;
import com.chatapp.model.PrivateMessage;
import com.chatapp.model.User;

/**
 * Observer interface for the Observer Pattern.
 * Defines the contract for objects that need to be notified of chat events.
 * This demonstrates the Behavioral Design Pattern: Observer Pattern.
 */
public interface ChatObserver {
    
    /**
     * Called when a new message is received in the chat room
     * @param message The message that was received
     */
    void onMessageReceived(Message message);
    
    /**
     * Called when a user joins the chat room
     * @param user The user who joined
     */
    void onUserJoined(User user);
    
    /**
     * Called when a user leaves the chat room
     * @param user The user who left
     */
    void onUserLeft(User user);
    
    /**
     * Called when an error occurs in the chat room
     * @param errorMessage The error message
     */
    void onError(String errorMessage);

    /**
     * Called when a private message is received by this observer.
     * @param privateMessage The private message that was received.
     */
    void onPrivateMessageReceived(PrivateMessage privateMessage);
    
    /**
     * Gets the user ID of this observer
     * @return The user ID
     */
    String getObserverUserId();
}
