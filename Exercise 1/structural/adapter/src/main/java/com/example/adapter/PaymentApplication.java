package com.example.adapter;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentApplication {
    private static final Logger logger = LoggerUtil.getLogger();
    private static final int MAX_RETRIES = 3; // For transient error handling

    public static void main(String[] args) {
        logger.info("Payment Application started.");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();
            String choice = getUserInput(scanner, "Enter your choice: ");

            try {
                switch (choice) {
                    case "1":
                        processPaymentWithModernGateway(scanner);
                        break;
                    case "2":
                        processPaymentWithOldSystemAdapter(scanner);
                        break;
                    case "3":
                        running = false;
                        logger.info("Exiting application.");
                        System.out.println("Thank you for using the Payment Application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        logger.warning("User entered an invalid menu choice: " + choice);
                        break;
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "An unexpected error occurred in the main loop: " + e.getMessage(), e);
                System.out.println("An internal error occurred. Please try again or contact support.");
            }
            System.out.println("\n----------------------------------------\n");
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("--- Payment System Menu ---");
        System.out.println("1. Process payment with Modern Gateway");
        System.out.println("2. Process payment with Old Payment System (via Adapter)");
        System.out.println("3. Exit");
    }

    private static String getUserInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static double getPaymentAmount(Scanner scanner) {
        double amount = -1.0;
        int retries = 0;
        while (amount < 0 && retries < MAX_RETRIES) {
            try {
                System.out.print("Enter payment amount: $");
                amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                if (amount <= 0) {
                    System.out.println("Payment amount must be positive. Please try again.");
                    logger.warning("User entered non-positive amount: " + amount);
                    amount = -1.0; // Reset to trigger loop again
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value for the amount.");
                logger.log(Level.WARNING, "Invalid amount input from user.", e);
                scanner.nextLine(); // Consume the invalid input
                amount = -1.0; // Reset to trigger loop again
                retries++;
            } catch (Exception e) {
                logger.log(Level.SEVERE, "An unexpected error occurred while getting payment amount: " + e.getMessage(), e);
                System.out.println("An unexpected error occurred. Please try again.");
                amount = -1.0;
                retries++;
            }
        }
        if (retries >= MAX_RETRIES) {
            logger.severe("Exceeded max retries for getting payment amount. Aborting operation.");
            System.out.println("Too many invalid attempts. Aborting payment operation.");
            return -1.0; // Indicate failure
        }
        return amount;
    }

    private static void processPaymentWithModernGateway(Scanner scanner) {
        logger.info("User chose to process payment with Modern Gateway.");
        double amount = getPaymentAmount(scanner);
        if (amount == -1.0) {
            return; // Aborted due to invalid input
        }

        PaymentGateway modernGateway = new ModernPaymentGateway();
        PaymentClient client = new PaymentClient(modernGateway);
        try {
            client.makePayment(amount);
            logger.info("Payment of $" + amount + " processed successfully with Modern Gateway.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing payment with Modern Gateway for amount $" + amount + ": " + e.getMessage(), e);
            System.out.println("Failed to process payment with Modern Gateway. Please try again.");
        }
    }

    private static void processPaymentWithOldSystemAdapter(Scanner scanner) {
        logger.info("User chose to process payment with Old Payment System via Adapter.");
        double amount = getPaymentAmount(scanner);
        if (amount == -1.0) {
            return; // Aborted due to invalid input
        }

        OldPaymentSystem oldSystem = new OldPaymentSystem();
        PaymentGateway adapter = new OldPaymentSystemAdapter(oldSystem);
        PaymentClient client = new PaymentClient(adapter);
        try {
            client.makePayment(amount);
            logger.info("Payment of $" + amount + " processed successfully with Old Payment System via Adapter.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing payment with Old Payment System via Adapter for amount $" + amount + ": " + e.getMessage(), e);
            System.out.println("Failed to process payment with Old Payment System. Please try again.");
        }
    }
}
