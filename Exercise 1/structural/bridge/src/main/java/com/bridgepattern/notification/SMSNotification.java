package com.bridgepattern.notification;

import com.bridgepattern.sender.NotificationSender;
import com.bridgepattern.util.AppLogger;

/**
 * Refined Abstraction for SMS notifications.
 * Extends the Notification abstract class.
 */
public class SMSNotification extends Notification {

    /**
     * Constructor for SMSNotification.
     * @param sender The NotificationSender implementation to use for sending SMS.
     */
    public SMSNotification(NotificationSender sender) {
        super(sender);
        AppLogger.info("SMSNotification initialized.");
    }

    @Override
    public boolean send(String recipient, String message) {
        // Defensive programming: Validate inputs
        if (recipient == null || recipient.trim().isEmpty()) {
            AppLogger.warning("Attempted to send SMS notification with null or empty recipient.");
            return false;
        }
        if (message == null || message.trim().isEmpty()) {
            AppLogger.warning("Attempted to send SMS notification with null or empty message.");
            return false;
        }

        AppLogger.info("Preparing to send SMS notification to " + recipient + " using " + sender.getClass().getSimpleName());
        // Delegate the actual sending to the concrete implementor
        boolean success = sender.sendNotification(recipient, "SMS: " + message);
        if (success) {
            AppLogger.info("SMS notification successfully processed for " + recipient);
        } else {
            AppLogger.warning("SMS notification failed to process for " + recipient);
        }
        return success;
    }
}
