package com.chatapp.adapter;

import com.chatapp.model.Message;

/**
 * Interface defining the contract for communication protocols.
 * Part of the Adapter Pattern - defines what all protocols must implement.
 */
public interface CommunicationProtocol {
    
    /**
     * Sends a message using the protocol
     * @param message The message to send
     * @return true if message was sent successfully
     */
    boolean sendMessage(Message message);
    
    /**
     * Receives a message using the protocol
     * @return The received message, or null if none available
     */
    Message receiveMessage();
    
    /**
     * Connects using this protocol
     * @return true if connection was successful
     */
    boolean connect();
    
    /**
     * Disconnects from this protocol
     */
    void disconnect();
    
    /**
     * Checks if the protocol is currently connected
     * @return true if connected
     */
    boolean isConnected();
    
    /**
     * Gets the protocol name
     * @return The name of the protocol
     */
    String getProtocolName();
}
