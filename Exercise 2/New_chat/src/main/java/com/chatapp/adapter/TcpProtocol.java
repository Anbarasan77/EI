package com.chatapp.adapter;

import com.chatapp.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TCP protocol implementation.
 * Demonstrates the Adapter Pattern - adapts TCP socket communication to the common interface.
 * Simulates TCP socket behavior for the console application.
 */
public class TcpProtocol implements CommunicationProtocol {
    private static final Logger logger = LoggerFactory.getLogger(TcpProtocol.class);
    
    private boolean connected;
    private final BlockingQueue<Message> messageQueue;
    private final String host;
    private final int port;
    
    public TcpProtocol(String host, int port) {
        this.host = host;
        this.port = port;
        this.messageQueue = new LinkedBlockingQueue<>();
        this.connected = false;
    }
    
    @Override
    public boolean sendMessage(Message message) {
        if (!connected) {
            logger.warn("Cannot send message via TCP: Not connected");
            return false;
        }
        
        try {
            // Simulate TCP socket message sending
            logger.debug("Sending message via TCP to {}:{}: {}", host, port, message.getContent());
            messageQueue.offer(message);
            return true;
        } catch (Exception e) {
            logger.error("Error sending message via TCP: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public Message receiveMessage() {
        if (!connected) {
            logger.warn("Cannot receive message via TCP: Not connected");
            return null;
        }
        
        try {
            // Simulate TCP socket message receiving
            return messageQueue.poll();
        } catch (Exception e) {
            logger.error("Error receiving message via TCP: {}", e.getMessage(), e);
            return null;
        }
    }
    
    @Override
    public boolean connect() {
        if (connected) {
            logger.debug("TCP already connected to {}:{}", host, port);
            return true;
        }
        
        try {
            // Simulate TCP socket connection
            logger.info("Establishing TCP connection to {}:{}", host, port);
            connected = true;
            logger.info("TCP connected successfully to {}:{}", host, port);
            return true;
        } catch (Exception e) {
            logger.error("Failed to connect TCP to {}:{}: {}", host, port, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public void disconnect() {
        if (!connected) {
            return;
        }
        
        try {
            logger.info("Disconnecting TCP from {}:{}", host, port);
            connected = false;
            messageQueue.clear();
            logger.info("TCP disconnected successfully");
        } catch (Exception e) {
            logger.error("Error disconnecting TCP: {}", e.getMessage(), e);
        }
    }
    
    @Override
    public boolean isConnected() {
        return connected;
    }
    
    @Override
    public String getProtocolName() {
        return "TCP";
    }
}
