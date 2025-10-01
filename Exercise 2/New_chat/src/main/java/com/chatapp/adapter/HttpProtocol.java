package com.chatapp.adapter;

import com.chatapp.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * HTTP protocol implementation.
 * Demonstrates the Adapter Pattern - adapts HTTP communication to the common interface.
 * Simulates HTTP polling behavior for the console application.
 */
public class HttpProtocol implements CommunicationProtocol {
    private static final Logger logger = LoggerFactory.getLogger(HttpProtocol.class);
    
    private boolean connected;
    private final BlockingQueue<Message> messageQueue;
    private final String serverUrl;
    
    public HttpProtocol(String serverUrl) {
        this.serverUrl = serverUrl;
        this.messageQueue = new LinkedBlockingQueue<>();
        this.connected = false;
    }
    
    @Override
    public boolean sendMessage(Message message) {
        if (!connected) {
            logger.warn("Cannot send message via HTTP: Not connected");
            return false;
        }
        
        try {
            // Simulate HTTP POST request
            logger.debug("Sending HTTP POST request to {}: {}", serverUrl, message.getContent());
            messageQueue.offer(message);
            return true;
        } catch (Exception e) {
            logger.error("Error sending message via HTTP: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public Message receiveMessage() {
        if (!connected) {
            logger.warn("Cannot receive message via HTTP: Not connected");
            return null;
        }
        
        try {
            // Simulate HTTP GET request for polling
            logger.debug("Polling for messages via HTTP GET from {}", serverUrl);
            return messageQueue.poll();
        } catch (Exception e) {
            logger.error("Error receiving message via HTTP: {}", e.getMessage(), e);
            return null;
        }
    }
    
    @Override
    public boolean connect() {
        if (connected) {
            logger.debug("HTTP already connected to {}", serverUrl);
            return true;
        }
        
        try {
            // Simulate HTTP connection validation
            logger.info("Establishing HTTP connection to {}", serverUrl);
            connected = true;
            logger.info("HTTP connected successfully to {}", serverUrl);
            return true;
        } catch (Exception e) {
            logger.error("Failed to connect HTTP to {}: {}", serverUrl, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public void disconnect() {
        if (!connected) {
            return;
        }
        
        try {
            logger.info("Disconnecting HTTP from {}", serverUrl);
            connected = false;
            messageQueue.clear();
            logger.info("HTTP disconnected successfully");
        } catch (Exception e) {
            logger.error("Error disconnecting HTTP: {}", e.getMessage(), e);
        }
    }
    
    @Override
    public boolean isConnected() {
        return connected;
    }
    
    @Override
    public String getProtocolName() {
        return "HTTP";
    }
}
