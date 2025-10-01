package com.bridgepattern.notification;

import com.bridgepattern.sender.NotificationSender;
import com.bridgepattern.util.AppLogger;

/**
 * Refined Abstraction for email notifications.
 * Extends the Notification abstract class.
 */
public class EmailNotification extends Notification {

    /**
     * Constructor for EmailNotification.
     * @param sender The NotificationSender implementation to use for sending emails.
     */
    public EmailNotification(NotificationSender sender) {
        super(sender);
        AppLogger.info("EmailNotification initialized.");
    }

    @Override
    public boolean send(String recipient, String message) {
        // Defensive programming: Validate inputs
        if (recipient == null || recipient.trim().isEmpty()) {
            AppLogger.warning("Attempted to send email notification with null or empty recipient.");
            return false;
        }
        if (message == null || message.trim().isEmpty()) {
            AppLogger.warning("Attempted to send email notification with null or empty message.");
            return false;
        }

        AppLogger.info("Preparing to send email notification to " + recipient + " using " + sender.getClass().getSimpleName());
        // Delegate the actual sending to the concrete implementor
        boolean success = sender.sendNotification(recipient, "Email: " + message);
        if (success) {
            AppLogger.info("Email notification successfully processed for " + recipient);
        } else {
            AppLogger.warning("Email notification failed to process for " + recipient);
        }
        return success;
    }
}
