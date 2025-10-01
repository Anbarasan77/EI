package com.bridgepattern.sender;

import com.bridgepattern.util.AppLogger;

/**
 * Concrete Implementor for sending SMS notifications.
 * Implements the NotificationSender interface.
 */
public class SMSSender implements NotificationSender {

    @Override
    public boolean sendNotification(String recipient, String message) {
        // Defensive programming: Validate inputs
        if (recipient == null || recipient.trim().isEmpty()) {
            AppLogger.warning("Attempted to send SMS with null or empty recipient.");
            return false;
        }
        if (message == null || message.trim().isEmpty()) {
            AppLogger.warning("Attempted to send SMS with null or empty message to " + recipient);
            return false;
        }

        // Simulate transient error handling: 15% chance of failure
        if (Math.random() < 0.15) {
            AppLogger.warning("Simulated transient error: SMS sending failed for " + recipient);
            // In a real application, this would involve retries or circuit breakers
            return false;
        }

        try {
            // Simulate SMS sending logic
            AppLogger.info("SMS sent to " + recipient + ": " + message);
            return true;
        } catch (Exception e) {
            // Exception handling
            AppLogger.error("Failed to send SMS to " + recipient, e);
            return false;
        }
    }
}
