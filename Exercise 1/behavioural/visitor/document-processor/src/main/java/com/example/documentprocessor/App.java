package com.example.documentprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final Scanner scanner = new Scanner(System.in);
    private static Document currentDocument = new Document();
    private static boolean running = true;

    public static void main(String[] args) {
        logger.info("Document Processor Application Started.");
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            processChoice(choice);
        }
        logger.info("Document Processor Application Exited.");
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- Document Processor Menu ---");
        System.out.println("1. Add Heading Element");
        System.out.println("2. Add Paragraph Element");
        System.out.println("3. Add Image Element");
        System.out.println("4. Export Document to HTML");
        System.out.println("5. Export Document to Plain Text");
        System.out.println("6. View Current Document Elements");
        System.out.println("7. Create New Document");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            logger.warn("Invalid input: Please enter a number.", e);
            System.out.println("Invalid input. Please enter a number between 1 and 8.");
            scanner.next(); // Consume the invalid input
            return -1; // Indicate invalid choice
        } finally {
            scanner.nextLine(); // Consume the rest of the line
        }
    }

    private static void processChoice(int choice) {
        try {
            switch (choice) {
                case 1:
                    addHeadingElement();
                    break;
                case 2:
                    addParagraphElement();
                    break;
                case 3:
                    addImageElement();
                    break;
                case 4:
                    exportDocumentToHtml();
                    break;
                case 5:
                    exportDocumentToPlainText();
                    break;
                case 6:
                    viewCurrentDocumentElements();
                    break;
                case 7:
                    createNewDocument();
                    break;
                case 8:
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    logger.warn("User entered an invalid menu choice: {}", choice);
            }
        } catch (Exception e) {
            logger.error("An unexpected error occurred while processing choice {}: {}", choice, e.getMessage(), e);
            System.out.println("An unexpected error occurred. Please try again.");
        }
    }

    private static void addHeadingElement() {
        System.out.print("Enter heading text: ");
        String text = scanner.nextLine();
        System.out.print("Enter heading level (1-6): ");
        try {
            int level = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (level >= 1 && level <= 6) {
                currentDocument.addElement(new HeadingElement(text, level));
                System.out.println("Heading added successfully.");
                logger.info("Added HeadingElement: '{}' (Level {})", text, level);
            } else {
                System.out.println("Invalid heading level. Must be between 1 and 6.");
                logger.warn("Attempted to add heading with invalid level: {}", level);
            }
        } catch (InputMismatchException e) {
            logger.warn("Invalid input for heading level.", e);
            System.out.println("Invalid input. Please enter a number for heading level.");
            scanner.next(); // Consume invalid input
            scanner.nextLine(); // Consume newline
        } catch (IllegalArgumentException e) {
            logger.error("Error adding heading element: {}", e.getMessage(), e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addParagraphElement() {
        System.out.print("Enter paragraph text: ");
        String text = scanner.nextLine();
        try {
            currentDocument.addElement(new ParagraphElement(text));
            System.out.println("Paragraph added successfully.");
            logger.info("Added ParagraphElement: '{}'", text);
        } catch (IllegalArgumentException e) {
            logger.error("Error adding paragraph element: {}", e.getMessage(), e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addImageElement() {
        System.out.print("Enter image URL: ");
        String url = scanner.nextLine();
        System.out.print("Enter image alt text: ");
        String altText = scanner.nextLine();
        try {
            currentDocument.addElement(new ImageElement(url, altText));
            System.out.println("Image added successfully.");
            logger.info("Added ImageElement: URL='{}', AltText='{}'", url, altText);
        } catch (IllegalArgumentException e) {
            logger.error("Error adding image element: {}", e.getMessage(), e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void exportDocumentToHtml() {
        if (currentDocument.getElements().isEmpty()) {
            System.out.println("Document is empty. Add elements before exporting.");
            logger.warn("Attempted to export empty document to HTML.");
            return;
        }
        HtmlExportVisitor htmlVisitor = new HtmlExportVisitor();
        try {
            currentDocument.accept(htmlVisitor);
            System.out.println("\n--- HTML Export ---");
            System.out.println(htmlVisitor.getHtmlOutput());
            System.out.println("-------------------");
            logger.info("Document exported to HTML.");
        } catch (Exception e) {
            logger.error("Error exporting document to HTML: {}", e.getMessage(), e);
            System.out.println("Error during HTML export: " + e.getMessage());
        }
    }

    private static void exportDocumentToPlainText() {
        if (currentDocument.getElements().isEmpty()) {
            System.out.println("Document is empty. Add elements before exporting.");
            logger.warn("Attempted to export empty document to Plain Text.");
            return;
        }
        PlainTextExportVisitor plainTextVisitor = new PlainTextExportVisitor();
        try {
            currentDocument.accept(plainTextVisitor);
            System.out.println("\n--- Plain Text Export ---");
            System.out.println(plainTextVisitor.getPlainTextOutput());
            System.out.println("-------------------------");
            logger.info("Document exported to Plain Text.");
        } catch (Exception e) {
            logger.error("Error exporting document to Plain Text: {}", e.getMessage(), e);
            System.out.println("Error during Plain Text export: " + e.getMessage());
        }
    }

    private static void viewCurrentDocumentElements() {
        if (currentDocument.getElements().isEmpty()) {
            System.out.println("The current document is empty.");
            return;
        }
        System.out.println("\n--- Current Document Elements ---");
        for (int i = 0; i < currentDocument.getElements().size(); i++) {
            DocumentElement element = currentDocument.getElements().get(i);
            String type = element.getClass().getSimpleName();
            String content = "";
            if (element instanceof HeadingElement) {
                HeadingElement h = (HeadingElement) element;
                content = String.format("Text: '%s', Level: %d", h.getText(), h.getLevel());
            } else if (element instanceof ParagraphElement) {
                ParagraphElement p = (ParagraphElement) element;
                content = String.format("Text: '%s'", p.getText());
            } else if (element instanceof ImageElement) {
                ImageElement img = (ImageElement) element;
                content = String.format("URL: '%s', Alt: '%s'", img.getUrl(), img.getAltText());
            }
            System.out.printf("%d. %s: %s\n", (i + 1), type, content);
        }
        System.out.println("---------------------------------");
        logger.info("Viewed current document elements.");
    }

    private static void createNewDocument() {
        currentDocument = new Document();
        System.out.println("New document created. All previous elements cleared.");
        logger.info("New document created.");
    }
}
