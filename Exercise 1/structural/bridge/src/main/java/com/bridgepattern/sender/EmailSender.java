package com.bridgepattern.sender;

import com.bridgepattern.util.AppLogger;

/**
 * Concrete Implementor for sending email notifications.
 * Implements the NotificationSender interface.
 */
public class EmailSender implements NotificationSender {

    @Override
    public boolean sendNotification(String recipient, String message) {
        // Defensive programming: Validate inputs
        if (recipient == null || recipient.trim().isEmpty()) {
            AppLogger.warning("Attempted to send email with null or empty recipient.");
            return false;
        }
        if (message == null || message.trim().isEmpty()) {
            AppLogger.warning("Attempted to send email with null or empty message to " + recipient);
            return false;
        }

        // Simulate transient error handling: 10% chance of failure
        if (Math.random() < 0.1) {
            AppLogger.warning("Simulated transient error: Email sending failed for " + recipient);
            // In a real application, this would involve retries or circuit breakers
            return false;
        }

        try {
            // Simulate email sending logic
            AppLogger.info("Email sent to " + recipient + ": " + message);
            return true;
        } catch (Exception e) {
            // Exception handling
            AppLogger.error("Failed to send email to " + recipient, e);
            return false;
        }
    }
}
