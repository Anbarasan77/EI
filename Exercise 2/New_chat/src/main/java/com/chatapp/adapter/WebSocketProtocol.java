package com.chatapp.adapter;

import com.chatapp.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * WebSocket protocol implementation.
 * Demonstrates the Adapter Pattern - adapts WebSocket communication to the common interface.
 * Simulates WebSocket behavior for the console application.
 */
public class WebSocketProtocol implements CommunicationProtocol {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketProtocol.class);
    
    private boolean connected;
    private final BlockingQueue<Message> messageQueue;
    private final String endpoint;
    
    public WebSocketProtocol(String endpoint) {
        this.endpoint = endpoint;
        this.messageQueue = new LinkedBlockingQueue<>();
        this.connected = false;
    }
    
    @Override
    public boolean sendMessage(Message message) {
        if (!connected) {
            logger.warn("Cannot send message via WebSocket: Not connected");
            return false;
        }
        
        try {
            // Simulate WebSocket message sending
            logger.debug("Sending message via WebSocket to {}: {}", endpoint, message.getContent());
            messageQueue.offer(message);
            return true;
        } catch (Exception e) {
            logger.error("Error sending message via WebSocket: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public Message receiveMessage() {
        if (!connected) {
            logger.warn("Cannot receive message via WebSocket: Not connected");
            return null;
        }
        
        try {
            // Non-blocking poll
            return messageQueue.poll();
        } catch (Exception e) {
            logger.error("Error receiving message via WebSocket: {}", e.getMessage(), e);
            return null;
        }
    }
    
    @Override
    public boolean connect() {
        if (connected) {
            logger.debug("WebSocket already connected to {}", endpoint);
            return true;
        }
        
        try {
            // Simulate WebSocket connection
            logger.info("Establishing WebSocket connection to {}", endpoint);
            connected = true;
            logger.info("WebSocket connected successfully to {}", endpoint);
            return true;
        } catch (Exception e) {
            logger.error("Failed to connect WebSocket to {}: {}", endpoint, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public void disconnect() {
        if (!connected) {
            return;
        }
        
        try {
            logger.info("Disconnecting WebSocket from {}", endpoint);
            connected = false;
            messageQueue.clear();
            logger.info("WebSocket disconnected successfully");
        } catch (Exception e) {
            logger.error("Error disconnecting WebSocket: {}", e.getMessage(), e);
        }
    }
    
    @Override
    public boolean isConnected() {
        return connected;
    }
    
    @Override
    public String getProtocolName() {
        return "WebSocket";
    }
}
