# Document Management System - Prototype Design Pattern Demonstration

This project demonstrates the **Prototype design pattern** using a simple Document Management System implemented in Java. It allows users to create new documents (Reports, Presentations, Letters) by cloning existing prototype instances, thereby avoiding the overhead of creating new objects from scratch and ensuring consistency.

## Design Pattern: Prototype

The Prototype design pattern is a creational design pattern that allows you to create new objects by copying an existing object, known as the prototype. This pattern is particularly useful when the cost of creating a new object is expensive or complex, or when you want to ensure that new objects are created with a predefined initial state.

In this application:
*   **Prototype Interface:** The `Document` interface (`src/com/prototype/document/Document.java`) defines the `cloneDocument()` method, which all concrete document types must implement. It also provides common methods for setting and getting document title and content.
*   **Concrete Prototypes:** `Report`, `Presentation`, and `Letter` (`src/com/prototype/document/*.java`) are concrete implementations of the `Document` interface. Each provides its own implementation of `cloneDocument()` to create a deep or shallow copy of itself, along with specific fields relevant to its document type.
*   **Registry/Client:** The `DocumentRegistry` (`src/com/prototype/document/DocumentRegistry.java`) acts as a central repository for managing prototype documents. The `Main` class (`src/com/prototype/Main.java`) is the client that requests cloned documents from the registry and then customizes them.

## Project Structure

The project is organized into the following files, adhering to best practices where each class/interface resides in its own `.java` file within the `com.prototype` package:

```
.
├── bin/                        # Compiled Java bytecode (.class files)
│   └── com/
│       └── prototype/
│           ├── Main.class
│           └── document/
│               ├── Document.class
│               ├── DocumentRegistry.class
│               ├── Letter.class
│               ├── Presentation.class
│               └── Report.class
└── src/                        # Java source code files
    └── com/
        └── prototype/
            ├── Main.java
            └── document/
                ├── Document.java
                ├── DocumentRegistry.java
                ├── Letter.java
                ├── Presentation.java
                └── Report.java
└── README.md                   # This README file
```

## How to Run the Application

Follow these steps to compile and run the Document Management System application:

1.  **Navigate to the Project Directory:**
    Open your terminal or command prompt and navigate to the root directory of this project (e.g., `c:\EI\Exercise 1\creational\prototype`).

    ```bash
    cd c:\EI\Exercise 1\creational\prototype
    ```

2.  **Compile the Java Source Files:**
    Compile all the `.java` files located in the `src` directory. This will create `.class` files in the `bin` directory.

    ```bash
    javac -d bin src/com/prototype/**/*.java
    ```
    *(Note: The `**/*.java` pattern ensures all Java files in subdirectories are compiled.)*

3.  **Run the Application:**
    Execute the `Main` class from the `bin` directory.

    ```bash
    java -cp bin com.prototype.Main
    ```

4.  **Interact with the Application:**
    *   The application will present a menu to create and edit different document types.
    *   Choose an option (1-3) to clone a prototype document.
    *   You will then be prompted to enter new details (title, content, and specific fields like author/presenter/recipient).
    *   The cloned and edited document's details will be displayed.
    *   Choose option `4` to exit the application.

## Sample Interactions

Here's a typical flow of interaction:

1.  **Start Application:**
    ```
    java -cp bin com.prototype.Main
    ```
    _Output:_
    ```
    --- Document Management System ---
    1. Create and Edit a Report
    2. Create and Edit a Presentation
    3. Create and Edit a Letter
    4. Exit
    Enter your choice:
    ```

2.  **Create and Edit a Report:**
    *   **Input:** `1`
    *   _Output:_ Prompts for new title, content, author, date.
    *   **Sample Input:**
        *   New title: `Monthly Sales Report`
        *   New content: `Detailed analysis of Q1 sales figures.`
        *   New author: `Jane Doe`
        *   New date: `2025-03-31`
    *   _Effect:_ A new Report document is created by cloning the prototype, and its details are displayed with the new information. The original prototype remains unchanged.

3.  **Create and Edit a Presentation:**
    *   **Input:** `2`
    *   _Output:_ Prompts for new title, content, presenter, number of slides.
    *   **Sample Input:**
        *   New title: `Project Alpha Kickoff`
        *   New content: `Overview of Project Alpha goals and timeline.`
        *   New presenter: `John Smith`
        *   New number of slides: `15`
    *   _Effect:_ A new Presentation document is created by cloning the prototype, and its details are displayed.

4.  **Exit Application:**
    *   **Input:** `4`
    *   _Effect:_ Application terminates.

## Key Features and Best Practices

*   **Code Organization:** Each class and interface is in its own `.java` file, following standard Java package structure.
*   **Naming Conventions:** Standard Java naming conventions are followed throughout the codebase.
*   **Logging Mechanism:** Uses `java.util.logging` for informative output and error tracking.
*   **Exception Handling:** Robust `try-catch` blocks are used for user input parsing, cloning operations, and unexpected errors.
*   **Defensive Programming:** Inputs are validated (e.g., `InputMismatchException` for menu choices, `NumberFormatException` for slide count) to ensure application stability.
*   **Cloning Mechanism:** Demonstrates how to implement `Cloneable` and override `clone()` for effective object copying.
