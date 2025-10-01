package com.bridgepattern.notification;

import com.bridgepattern.sender.NotificationSender;
import com.bridgepattern.util.AppLogger;

/**
 * The Abstraction for notifications.
 * Holds a reference to a NotificationSender (Implementor) and delegates the sending.
 */
public abstract class Notification {
    protected NotificationSender sender;

    /**
     * Constructor for Notification.
     * @param sender The NotificationSender implementation to use.
     * @throws IllegalArgumentException if sender is null.
     */
    public Notification(NotificationSender sender) {
        if (sender == null) {
            AppLogger.severe("NotificationSender cannot be null for Notification.");
            throw new IllegalArgumentException("NotificationSender cannot be null.");
        }
        this.sender = sender;
        AppLogger.info("Notification created with sender: " + sender.getClass().getSimpleName());
    }

    /**
     * Abstract method to send the notification.
     * Concrete notification types will implement this.
     * @param recipient The recipient of the notification.
     * @param message The message content.
     * @return true if the notification was sent successfully, false otherwise.
     */
    public abstract boolean send(String recipient, String message);
}
