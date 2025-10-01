epackage com.bridgepattern;

import com.bridgepattern.notification.*;
import com.bridgepattern.sender.*;
import com.bridgepattern.util.AppLogger;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main application class to demonstrate the Bridge design pattern for a Notification System.
 * This program runs continuously, allowing users to send various types of notifications
 * using different sending mechanisms.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AppLogger.info("Notification System Application Started.");

        // Initialize different notification senders (Implementors)
        NotificationSender emailSender = new EmailSender();
        NotificationSender smsSender = new SMSSender();
        NotificationSender pushSender = new PushSender();

        // Initialize different notification types (Abstractions) with their respective senders
        // This demonstrates the Bridge pattern: decoupling abstraction from implementation
        Notification emailNotificationViaEmail = new EmailNotification(emailSender);
        Notification smsNotificationViaSMS = new SMSNotification(smsSender);
        Notification pushNotificationViaPush = new PushNotification(pushSender);

        // Demonstrate flexibility: Email notification sent via SMS sender (though not practical, shows decoupling)
        Notification emailNotificationViaSMS = new EmailNotification(smsSender);

        // Demonstrate flexibility: SMS notification sent via Email sender (though not practical, shows decoupling)
        Notification smsNotificationViaEmail = new SMSNotification(emailSender);


        // Main application loop
        while (true) { // Program is expected to run for a long time gathering inputs
            displayMenu();
            int choice = getUserChoice();

            if (choice == 0) {
                AppLogger.info("Exiting Notification System Application.");
                AppLogger.close();
                break;
            }

            String recipient = getValidatedInput("Enter recipient (e.g., email, phone number, device ID):");
            String message = getValidatedInput("Enter message content:");

            Notification selectedNotification = null;
            switch (choice) {
                case 1:
                    selectedNotification = emailNotificationViaEmail;
                    break;
                case 2:
                    selectedNotification = smsNotificationViaSMS;
                    break;
                case 3:
                    selectedNotification = pushNotificationViaPush;
                    break;
                case 4:
                    selectedNotification = emailNotificationViaSMS;
                    break;
                case 5:
                    selectedNotification = smsNotificationViaEmail;
                    break;
                default:
                    AppLogger.warning("Invalid notification choice. Please try again.");
                    continue; // Continue to the next iteration of the loop
            }

            if (selectedNotification != null) {
                AppLogger.info("Attempting to send notification...");
                boolean success = selectedNotification.send(recipient, message);
                if (success) {
                    System.out.println("Notification sent successfully!");
                } else {
                    System.out.println("Notification failed to send. Check logs for details.");
                }
            }
            System.out.println("\n----------------------------------------\n");
        }
        scanner.close();
    }

    /**
     * Displays the main menu options to the user.
     */
    private static void displayMenu() {
        System.out.println("Choose a notification type to send:");
        System.out.println("1. Email Notification (via Email Sender)");
        System.out.println("2. SMS Notification (via SMS Sender)");
        System.out.println("3. Push Notification (via Push Sender)");
        System.out.println("4. Email Notification (via SMS Sender - for demonstration)");
        System.out.println("5. SMS Notification (via Email Sender - for demonstration)");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Gets and validates the user's menu choice.
     * Handles invalid input gracefully.
     * @return The validated integer choice.
     */
    private static int getUserChoice() {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
                if (choice >= 0 && choice <= 5) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 0 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                AppLogger.warning("Invalid input for menu choice: " + e.getMessage());
            }
        }
    }

    /**
     * Gets validated string input from the user.
     * Ensures the input is not null or empty.
     * @param prompt The message to display to the user.
     * @return The validated string input.
     */
    private static String getValidatedInput(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt + " ");
            input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            } else {
                System.out.println("Input cannot be empty. Please try again.");
                AppLogger.warning("Empty input provided for prompt: " + prompt);
            }
        }
    }
}
