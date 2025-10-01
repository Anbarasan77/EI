package com.bridgepattern.notification;

import com.bridgepattern.sender.NotificationSender;
import com.bridgepattern.util.AppLogger;

/**
 * Refined Abstraction for push notifications.
 * Extends the Notification abstract class.
 */
public class PushNotification extends Notification {

    /**
     * Constructor for PushNotification.
     * @param sender The NotificationSender implementation to use for sending push notifications.
     */
    public PushNotification(NotificationSender sender) {
        super(sender);
        AppLogger.info("PushNotification initialized.");
    }

    @Override
    public boolean send(String recipient, String message) {
        // Defensive programming: Validate inputs
        if (recipient == null || recipient.trim().isEmpty()) {
            AppLogger.warning("Attempted to send push notification with null or empty recipient.");
            return false;
        }
        if (message == null || message.trim().isEmpty()) {
            AppLogger.warning("Attempted to send push notification with null or empty message.");
            return false;
        }

        AppLogger.info("Preparing to send push notification to " + recipient + " using " + sender.getClass().getSimpleName());
        // Delegate the actual sending to the concrete implementor
        boolean success = sender.sendNotification(recipient, "Push: " + message);
        if (success) {
            AppLogger.info("Push notification successfully processed for " + recipient);
        } else {
            AppLogger.warning("Push notification failed to process for " + recipient);
        }
        return success;
    }
}
