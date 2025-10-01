package com.bridgepattern.sender;

import com.bridgepattern.util.AppLogger;

/**
 * Concrete Implementor for sending push notifications.
 * Implements the NotificationSender interface.
 */
public class PushSender implements NotificationSender {

    @Override
    public boolean sendNotification(String recipient, String message) {
        // Defensive programming: Validate inputs
        if (recipient == null || recipient.trim().isEmpty()) {
            AppLogger.warning("Attempted to send push notification with null or empty recipient.");
            return false;
        }
        if (message == null || message.trim().isEmpty()) {
            AppLogger.warning("Attempted to send push notification with null or empty message to " + recipient);
            return false;
        }

        // Simulate transient error handling: 5% chance of failure
        if (Math.random() < 0.05) {
            AppLogger.warning("Simulated transient error: Push notification sending failed for " + recipient);
            // In a real application, this would involve retries or circuit breakers
            return false;
        }

        try {
            // Simulate push notification sending logic
            AppLogger.info("Push notification sent to " + recipient + ": " + message);
            return true;
        } catch (Exception e) {
            // Exception handling
            AppLogger.error("Failed to send push notification to " + recipient, e);
            return false;
        }
    }
}
