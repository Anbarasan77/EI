package com.bridgepattern.sender;

import com.bridgepattern.util.AppLogger;

/**
 * The Implementor interface for sending notifications.
 * Defines the contract for various notification sending mechanisms.
 */
public interface NotificationSender {
    /**
     * Sends a notification message to a specified recipient.
     *
     * @param recipient The recipient of the notification (e.g., email address, phone number).
     * @param message The content of the notification message.
     * @return true if the notification was sent successfully, false otherwise.
     */
    boolean sendNotification(String recipient, String message);
}
