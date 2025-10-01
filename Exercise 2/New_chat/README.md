# Real-Time Console Chat Application

## Problem Statement
Create a simple real-time chat application where users can join different chat rooms or create their own chat rooms. Users should be able to send and receive messages in real-time.

## Functional Requirements
1.  Allow users to create/join chat rooms by entering a unique room ID.
2.  Enable users to send and receive messages in real-time within a chat room.
3.  Display a list of active users in the chat room.
4.  Implement a private messaging feature between users.
5.  Implement message history, so the chat persists even if the user leaves and rejoins.

## Key Focus

### Design Patterns
*   **Behavioral Pattern**: Use the Observer Pattern to notify clients of new messages or user activities.
*   **Creational Pattern**: Use Singleton to manage the state of the chat rooms.
*   **Structural Pattern**: Use the Adapter Pattern to allow the system to work with different types of client communication protocols (WebSocket, HTTP, etc.).

### Object-Oriented Programming (OOP) Principles
*   Apply encapsulation, polymorphism, and inheritance effectively.

## Features

*   **Chat Room Management**: Create, join, and view active chat rooms.
*   **User Sessions**: Login and logout functionality to manage user presence.
*   **Public Messaging**: Send messages to all participants in a joined chat room.
*   **Private Messaging**: Send direct messages to specific users.
*   **Message History**: View past messages within a chat room.
*   **Active User List**: See who is currently in a chat room.
*   **Real-time Updates**: Messages and notifications are delivered in near real-time using the Observer pattern and dedicated polling threads.
*   **Pluggable Communication Protocols**: Demonstrates the Adapter pattern with mock HTTP, TCP, and WebSocket protocols (randomly assigned to user sessions).
*   **Robust Logging**: Integrated with SLF4J and Logback for detailed application logging.

## Technologies Used

- **Java 11**: The core programming language.
- **Maven**: Project build automation tool.
- **SLF4J & Logback**: For logging.
- **JUnit 4**: For unit testing (though no tests are currently implemented in the provided code).

## How to Build

This project uses Maven. To build the executable JAR (including all dependencies), navigate to the `New_chat` directory and run:

```bash
mvn clean install
```

This command will compile the source code, run tests (if any), and package the application into a shaded JAR file named `realtime-chat-application-shaded.jar` in the `New_chat/target/` directory.

## How to Run

After building the project, you can run the application from the `New_chat` directory using the following command:

```bash
java -jar target/realtime-chat-application-shaded.jar
```

## Usage

Upon starting the application, you will be presented with a main menu:

```
--- Main Menu ---
1. Create Chat Room
2. Join Chat Room
3. Login (to manage your session)
4. View All Chat Rooms
5. Logout (from current session)
Type 'exit' to quit.
Enter your choice:
```

### Main Menu Options:

- **1. Create Chat Room**:
  - Prompts for a Room ID and Room Name.
  - Example: `Enter desired Room ID: myroom`, `Enter desired Room Name: My Awesome Chat`
- **2. Join Chat Room**:
  - Prompts for your username and the Room ID to join.
  - A `UserSession` will be created for you, and you will enter the chat room interface.
- **3. Login**:
  - If you previously joined a room and then left (using `/leave`), your session might still be active. You can log back in using your User ID (displayed when you first join a room).
- **4. View All Chat Rooms**:
  - Displays a list of all currently active chat rooms, their IDs, names, and the number of users.
- **5. Logout**:
  - Logs out a user from their active session, stopping message polling and removing them from any joined rooms.
- **Type 'exit'**: Quits the application.

### Inside a Chat Room:

Once you join a chat room, you can interact using various commands:

- **Send Public Message**: Simply type your message and press Enter.
  - Example: `Hello everyone!`
- **`/users`**: Displays a list of all active users in the current chat room.
- **`/history`**: Shows the message history of the current chat room.
- **`/msg <username> <message>`**: Sends a private message to a specific user.
  - Example: `/msg Alice Hi Alice, how are you?`
- **`/leave`**: Leaves the current chat room. Your session might remain active, allowing you to log back in later.

## Logging

The application uses Logback for logging. Log files are generated in the `logs/` directory:

- `logs/chat-application.log`: General application logs.
- `logs/chat-application-error.log`: Error-specific logs.
