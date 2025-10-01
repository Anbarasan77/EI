# Document Processor - Visitor Design Pattern Demonstration

This project demonstrates the **Visitor design pattern** using a simple document processing application in Java. The application allows users to create a document composed of various elements (headings, paragraphs, images) and then process these elements in different ways (e.g., export to HTML, export to plain text) without modifying the core document element classes.

## Design Pattern: Visitor

The Visitor pattern is a behavioral design pattern that allows you to add new operations to existing object structures without modifying those structures.

**Key Components in this application:**

1.  **`DocumentElement` (Visitable Interface):**
    *   `DocumentElement.java`: An interface that declares an `accept(DocumentVisitor visitor)` method. All concrete document elements implement this interface.
2.  **Concrete `DocumentElement` Classes (Visitable):**
    *   `HeadingElement.java`: Represents a heading in the document.
    *   `ParagraphElement.java`: Represents a paragraph in the document.
    *   `ImageElement.java`: Represents an image in the document.
    Each of these classes implements `DocumentElement` and provides its own `accept` method, which calls the corresponding `visit` method on the passed visitor.
3.  **`DocumentVisitor` (Visitor Interface):**
    *   `DocumentVisitor.java`: An interface that declares a `visit` method for each concrete `DocumentElement` type.
4.  **Concrete `DocumentVisitor` Classes (Visitor):**
    *   `HtmlExportVisitor.java`: Implements `DocumentVisitor` to generate an HTML representation of the document elements.
    *   `PlainTextExportVisitor.java`: Implements `DocumentVisitor` to generate a plain text representation of the document elements.
    Each visitor class encapsulates a specific operation to be performed on the elements.
5.  **`Document` (Object Structure):**
    *   `Document.java`: A class that holds a collection of `DocumentElement` objects. It provides a method `accept(DocumentVisitor visitor)` that iterates through its elements, calling `accept` on each one, effectively allowing the visitor to "visit" all elements in the document.
6.  **`App` (Client):**
    *   `App.java`: The main application class that provides a command-line interface for users to interact with the document, add elements, and trigger different export operations using the visitors.

## Project Structure

The project is a Maven-based Java application with the following structure:

```
document-processor/
├── pom.xml
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── documentprocessor/
│                       ├── App.java
│                       ├── Document.java
│                       ├── DocumentElement.java
│                       ├── DocumentVisitor.java
│                       ├── HeadingElement.java
│                       ├── HtmlExportVisitor.java
│                       ├── ImageElement.java
│                       ├── ParagraphElement.java
│                       └── PlainTextExportVisitor.java
└── README.md
```

## Features

*   **Extensible Operations:** Easily add new document processing operations (e.g., Markdown export, spell check, word count) by creating new `DocumentVisitor` implementations without altering the `DocumentElement` classes.
*   **Clean Separation of Concerns:** Operations are separated from the object structure, promoting modularity.
*   **User-Friendly CLI:** An interactive command-line menu for adding document elements and exporting the document.
*   **Robustness:** Includes logging (SLF4J + Logback), exception handling, and defensive programming (input validations, null checks).

## How to Build and Run

This project uses Maven.

### Prerequisites

*   Java Development Kit (JDK) 11 or higher
*   Apache Maven

### Build Instructions

Navigate to the `document-processor` directory and run the Maven clean install command. The `maven-shade-plugin` is configured to create an "uber-jar" that includes all dependencies.

```bash
cd document-processor
mvn clean install
```

### Run Instructions

After a successful build, you can run the application using the generated JAR file:

```bash
cd document-processor
java -jar target/document-processor-1.0-SNAPSHOT.jar
```

### Interactive Usage

Once the application is running, you will see a menu. Here's an example input flow:

1.  **Add a Heading Element:**
    *   Enter `1`
    *   Enter heading text: `Introduction to Design Patterns`
    *   Enter heading level (1-6): `1`

2.  **Add a Paragraph Element:**
    *   Enter `2`
    *   Enter paragraph text: `The Visitor design pattern is a behavioral design pattern that allows you to add new operations to existing object structures without modifying those structures.`

3.  **Add an Image Element:**
    *   Enter `3`
    *   Enter image URL: `https://example.com/visitor_pattern.png`
    *   Enter image alt text: `Visitor Pattern Diagram`

4.  **View Current Document Elements:**
    *   Enter `6`

5.  **Add another Paragraph Element:**
    *   Enter `2`
    *   Enter paragraph text: `This application demonstrates how to use the Visitor pattern for document processing, allowing different export formats.`

6.  **Export Document to HTML:**
    *   Enter `4`

7.  **Export Document to Plain Text:**
    *   Enter `5`

8.  **Exit the application:**
    *   Enter `8`
