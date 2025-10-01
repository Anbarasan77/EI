# Bridge Design Pattern Demonstration: Notification System

## Project Overview

This project demonstrates the **Bridge design pattern** using a flexible and extensible **Notification System**. The goal is to decouple the abstraction (what kind of notification to send, e.g., Email, SMS, Push) from its implementation (how to send it, e.g., via Email service, SMS gateway, Push notification service). This allows both the notification types and the sending mechanisms to evolve independently.

The application is designed to run continuously, gathering user inputs to send various types of notifications through different channels, showcasing the power and flexibility of the Bridge pattern.

## Design Pattern: Bridge

The Bridge pattern is a structural design pattern that separates an object's abstraction from its implementation, allowing both to vary independently.

In this project:

- **Abstraction**: Represented by the `Notification` abstract class and its refined abstractions (`EmailNotification`, `SMSNotification`, `PushNotification`). These classes define _what_ kind of notification is being sent.
- **Implementor**: Represented by the `NotificationSender` interface and its concrete implementors (`EmailSender`, `SMSSender`, `PushSender`). These classes define _how_ the notification is sent.

The `Notification` classes hold a reference to a `NotificationSender` and delegate the actual sending process to it, thus bridging the two hierarchies.

## Project Structure

```
.
├── src
│   └── main
│       └── java
│           └── com
│               └── bridgepattern
│                   ├── Main.java
│                   ├── notification
│                   │   ├── EmailNotification.java
│                   │   ├── Notification.java
│                   │   ├── PushNotification.java
│                   │   └── SMSNotification.java
│                   ├── sender
│                   │   ├── EmailSender.java
│                   │   ├── NotificationSender.java
│                   │   ├── PushSender.java
│                   │   └── SMSSender.java
│                   └── util
│                       └── AppLogger.java
└── bin/ (compiled classes will be here)
└── app.log (application logs will be written here)
└── README.md
```

## Key Features and Best Practices

- **Modular Design**: Each class is in a separate file, adhering to standard Java package and naming conventions.
- **Logging Mechanism**: A custom `AppLogger` utility (using `java.util.logging`) provides centralized logging to both the console and an `app.log` file, capturing informational messages, warnings, and errors.
- **Exception Handling**: Robust `try-catch` blocks are implemented in sender classes and input handling to gracefully manage potential runtime exceptions.
- **Transient Error Handling**: Simulated transient failures (e.g., network issues) are included in `NotificationSender` implementations to demonstrate where retry logic or circuit breakers would be applied in a real-world scenario.
- **Defensive Programming**: Comprehensive input validations are performed at various levels (user input, notification constructors, sender methods) to prevent `NullPointerException` and handle invalid data.
- **Long-Running Application**: The `Main` class features a continuous loop, allowing users to interact with the system multiple times without restarting, as per requirements.
- **User-Friendly Interface**: A menu-driven command-line interface guides the user through notification choices and input.

## How to Compile and Run

To compile and run this Java application, follow these steps:

1.  **Navigate to the project root directory**:

    ```bash
    cd c:\EI\Exercise 1\structural\bridge
    ```

2.  **Compile the Java source files**:
    This command compiles all `.java` files and places the compiled `.class` files into the `bin` directory.

    ```bash
    javac -d bin -sourcepath src\main\java src\main\java\com\bridgepattern\Main.java src\main\java\com\bridgepattern\notification\*.java src\main\java\com\bridgepattern\sender\*.java src\main\java\com\bridgepattern\util\*.java
    ```

3.  **Run the application**:
    This command executes the `Main` class from the compiled `bin` directory.
    ```bash
    java -cp bin com.bridgepattern.Main
    ```

## Usage

Once the application is running, you will be presented with a menu:

```
Choose a notification type to send:
1. Email Notification (via Email Sender)
2. SMS Notification (via SMS Sender)
3. Push Notification (via Push Sender)
4. Email Notification (via SMS Sender - for demonstration)
5. SMS Notification (via Email Sender - for demonstration)
0. Exit
Enter your choice:
```

1.  **Select a notification type** by entering the corresponding number (1-5).
2.  **Enter the recipient** (e.g., an email address, phone number, or device ID).
3.  **Enter the message content**.
4.  The system will attempt to send the notification and report success or failure. Check the console output and the `app.log` file for detailed logs.
5.  You can continue sending notifications until you enter `0` to exit the application.

This setup allows you to experiment with different combinations of notification types and senders, observing how the Bridge pattern facilitates flexible and independent variations.
