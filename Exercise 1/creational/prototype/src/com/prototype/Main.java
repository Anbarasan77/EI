package com.prototype;

import com.prototype.document.*;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Main application class to demonstrate the Prototype design pattern
 * with a Document Management System.
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final DocumentRegistry DOCUMENT_REGISTRY = new DocumentRegistry();

    public static void main(String[] args) {
        setupLogger();
        LOGGER.log(Level.INFO, "Application started.");

        initializePrototypes();
        runApplicationLoop();

        SCANNER.close();
        LOGGER.log(Level.INFO, "Application terminated.");
    }

    /**
     * Configures the logger to output to console with a simple format.
     */
    private static void setupLogger() {
        Logger rootLogger = Logger.getLogger("");
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        rootLogger.addHandler(consoleHandler);
        rootLogger.setLevel(Level.INFO); // Set default logging level
        // Prevent console handler from adding logs twice if root logger already has one
        rootLogger.setUseParentHandlers(false);
        consoleHandler.setLevel(Level.INFO); // Ensure console handler shows INFO and above
    }

    /**
     * Initializes the document registry with prototype documents.
     */
    private static void initializePrototypes() {
        try {
            DOCUMENT_REGISTRY.registerDocument("Report", new Report("Initial Report", "This is a standard report template.", "Admin", "2025-01-01"));
            DOCUMENT_REGISTRY.registerDocument("Presentation", new Presentation("Initial Presentation", "Standard presentation outline.", 10, "Presenter A"));
            DOCUMENT_REGISTRY.registerDocument("Letter", new Letter("Initial Letter", "Generic letter content.", "Recipient", "Sender"));
            LOGGER.log(Level.INFO, "Prototype documents initialized.");
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Error initializing prototypes: {0}", e.getMessage());
            System.err.println("Fatal error during prototype initialization. Exiting.");
            System.exit(1);
        }
    }

    /**
     * Runs the main application loop, presenting a menu to the user.
     */
    private static void runApplicationLoop() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();

            try {
                switch (choice) {
                    case 1:
                        createAndEditDocument("Report");
                        break;
                    case 2:
                        createAndEditDocument("Presentation");
                        break;
                    case 3:
                        createAndEditDocument("Letter");
                        break;
                    case 4:
                        running = false;
                        LOGGER.log(Level.INFO, "User chose to exit.");
                        break;
                    default:
                        LOGGER.log(Level.WARNING, "Invalid menu choice: {0}", choice);
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (CloneNotSupportedException e) {
                LOGGER.log(Level.SEVERE, "Cloning failed: {0}", e.getMessage());
                System.err.println("Error: Could not clone document. " + e.getMessage());
            } catch (IllegalArgumentException e) {
                LOGGER.log(Level.WARNING, "Validation error: {0}", e.getMessage());
                System.err.println("Input error: " + e.getMessage());
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "An unexpected error occurred.", e); // Corrected logging call
                System.err.println("An unexpected error occurred. Please check logs.");
            }
            System.out.println("\nPress Enter to continue...");
            SCANNER.nextLine(); // Consume the rest of the line after reading int, and wait for user to press enter
        }
    }

    /**
     * Displays the main menu options to the user.
     */
    private static void displayMenu() {
        System.out.println("\n--- Document Management System ---");
        System.out.println("1. Create and Edit a Report");
        System.out.println("2. Create and Edit a Presentation");
        System.out.println("3. Create and Edit a Letter");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Gets a valid integer choice from the user.
     * Handles InputMismatchException for invalid input.
     *
     * @return The user's integer choice.
     */
    private static int getUserChoice() {
        while (true) {
            try {
                int choice = SCANNER.nextInt();
                SCANNER.nextLine(); // Consume newline left-over
                return choice;
            } catch (InputMismatchException e) {
                LOGGER.log(Level.WARNING, "Invalid input type for menu choice.");
                System.out.print("Invalid input. Please enter a number: ");
                SCANNER.nextLine(); // Consume the invalid input
            }
        }
    }

    /**
     * Creates a new document by cloning a prototype and allows the user to edit it.
     *
     * @param documentType The type of document to create (e.g., "Report").
     * @throws CloneNotSupportedException if cloning fails.
     */
    private static void createAndEditDocument(String documentType) throws CloneNotSupportedException {
        LOGGER.log(Level.INFO, "Attempting to create and edit a {0}.", documentType);
        System.out.println("\nCreating a new " + documentType + " document...");

        Document newDocument = DOCUMENT_REGISTRY.getClonedDocument(documentType);
        System.out.println("Cloned " + documentType + " successfully. Original details:");
        newDocument.displayDocument();

        System.out.println("\n--- Edit " + documentType + " ---");
        System.out.print("Enter new title (current: " + newDocument.getTitle() + "): ");
        String newTitle = SCANNER.nextLine();
        if (!newTitle.trim().isEmpty()) {
            newDocument.setTitle(newTitle);
        }

        System.out.print("Enter new content (current: " + newDocument.getContent() + "): ");
        String newContent = SCANNER.nextLine();
        if (!newContent.trim().isEmpty()) {
            newDocument.setContent(newContent);
        }

        // Specific fields for each document type
        if (newDocument instanceof Report) {
            Report report = (Report) newDocument;
            System.out.print("Enter new author (current: " + report.getAuthor() + "): ");
            String newAuthor = SCANNER.nextLine();
            if (!newAuthor.trim().isEmpty()) {
                report.setAuthor(newAuthor);
            }
            System.out.print("Enter new date (current: " + report.getDate() + "): ");
            String newDate = SCANNER.nextLine();
            if (!newDate.trim().isEmpty()) {
                report.setDate(newDate);
            }
        } else if (newDocument instanceof Presentation) {
            Presentation presentation = (Presentation) newDocument;
            System.out.print("Enter new presenter (current: " + presentation.getPresenter() + "): ");
            String newPresenter = SCANNER.nextLine();
            if (!newPresenter.trim().isEmpty()) {
                presentation.setPresenter(newPresenter);
            }
            System.out.print("Enter new number of slides (current: " + presentation.getNumberOfSlides() + "): ");
            String slidesInput = SCANNER.nextLine();
            if (!slidesInput.trim().isEmpty()) {
                try {
                    int newSlides = Integer.parseInt(slidesInput);
                    presentation.setNumberOfSlides(newSlides);
                } catch (NumberFormatException e) {
                    LOGGER.log(Level.WARNING, "Invalid number format for slides: {0}", slidesInput);
                    System.err.println("Invalid input for number of slides. Keeping original.");
                }
            }
        } else if (newDocument instanceof Letter) {
            Letter letter = (Letter) newDocument;
            System.out.print("Enter new recipient (current: " + letter.getRecipient() + "): ");
            String newRecipient = SCANNER.nextLine();
            if (!newRecipient.trim().isEmpty()) {
                letter.setRecipient(newRecipient);
            }
            System.out.print("Enter new sender (current: " + letter.getSender() + "): ");
            String newSender = SCANNER.nextLine();
            if (!newSender.trim().isEmpty()) {
                letter.setSender(newSender);
            }
        }

        System.out.println("\nEdited " + documentType + " document details:");
        newDocument.displayDocument();
        LOGGER.log(Level.INFO, "Document {0} (Title: {1}) created and edited successfully.", new Object[]{documentType, newDocument.getTitle()});
    }
}
