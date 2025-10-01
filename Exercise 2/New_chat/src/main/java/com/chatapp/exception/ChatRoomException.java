package com.chatapp.exception;

/**
 * Custom exception for chat room related errors.
 * Demonstrates proper exception handling and error management.
 */
public class ChatRoomException extends RuntimeException {
    
    /**
     * Constructs a new ChatRoomException with the specified detail message
     * @param message The detail message
     */
    public ChatRoomException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new ChatRoomException with the specified detail message and cause
     * @param message The detail message
     * @param cause The cause
     */
    public ChatRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}
