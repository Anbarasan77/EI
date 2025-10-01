package com.chatapp;

import com.chatapp.adapter.CommunicationProtocol;
import com.chatapp.adapter.HttpProtocol;
import com.chatapp.adapter.TcpProtocol;
import com.chatapp.adapter.WebSocketProtocol;
import com.chatapp.exception.ChatRoomException;
import com.chatapp.manager.ChatRoomManager;
import com.chatapp.model.ChatRoom;
import com.chatapp.model.Message;
import com.chatapp.model.User;
import com.chatapp.observer.ChatObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional; // Added for finding users by username
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import com.chatapp.model.PrivateMessage; // Added for private messages

/**
 * Main application class for the real-time console chat. This class
 * orchestrates user interactions, chat room management, and demonstrates the
 * integration of various design patterns.
 */
public class ChatApplication {

    private static final Logger logger = LoggerFactory.getLogger(ChatApplication.class);
    private final ChatRoomManager chatRoomManager;
    private final BufferedReader consoleReader;
    private final Map<String, UserSession> activeUserSessions;
    private final ExecutorService messagePollingExecutor;

    public ChatApplication() {
        this.chatRoomManager = ChatRoomManager.getInstance();
        this.consoleReader = new BufferedReader(new InputStreamReader(System.in));
        this.activeUserSessions = new ConcurrentHashMap<>();
        // Use a cached thread pool for message polling to handle multiple users
        this.messagePollingExecutor = Executors.newCachedThreadPool();
    }

    public static void main(String[] args) {
        logger.info("Starting Real-Time Chat Application...");
        ChatApplication app = new ChatApplication();
        app.run();
        logger.info("Real-Time Chat Application stopped.");
    }

    public void run() {
        try {
            while (true) { // Loop indefinitely until user exits
                displayMainMenu();
                String choice = readLine("Enter your choice: ");

                switch (choice.trim().toLowerCase()) {
                    case "1":
                        handleCreateRoom();
                        break;
                    case "2":
                        handleJoinRoom();
                        break;
                    case "3":
                        handleLogin();
                        break;
                    case "4":
                        displayAllRooms();
                        break;
                    case "5":
                        handleLogout();
                        break;
                    case "exit":
                        shutdown();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        logger.warn("Invalid main menu choice: {}", choice);
                }
                System.out.println("\nPress Enter to continue...");
                readLine(""); // Wait for user to press Enter, using readLine to handle potential IOException
            }
        } catch (Exception e) { // Catch all other exceptions
            logger.error("An unexpected error occurred in main application loop: {}", e.getMessage(), e);
            System.err.println("An unexpected error occurred. Exiting.");
        } finally {
            shutdown();
        }
    }

    private void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Create Chat Room");
        System.out.println("2. Join Chat Room");
        System.out.println("3. Login (to manage your session)");
        System.out.println("4. View All Chat Rooms");
        System.out.println("5. Logout (from current session)");
        System.out.println("Type 'exit' to quit.");
    }

    private void handleCreateRoom() {
        System.out.println("\n--- Create Chat Room ---");
        String roomId = readLine("Enter desired Room ID: ");
        String roomName = readLine("Enter desired Room Name: ");

        try {
            ChatRoom newRoom = chatRoomManager.createChatRoom(roomId, roomName);
            System.out.println("Chat Room '" + newRoom.getRoomName() + "' (ID: " + newRoom.getRoomId() + ") created successfully!");
            logger.info("Room created: {}", roomId);
        } catch (IllegalArgumentException | ChatRoomException e) {
            System.err.println("Error creating room: " + e.getMessage());
            logger.error("Failed to create room {}: {}", roomId, e.getMessage());
        }
    }

    private void handleJoinRoom() {
        System.out.println("\n--- Join Chat Room ---");
        String username = readLine("Enter your username: ");
        String roomId = readLine("Enter Room ID to join: ");

        try {
            User user = new User(username);
            chatRoomManager.joinChatRoom(roomId, user);

            // Create a user session and register as an observer
            UserSession session = new UserSession(user, roomId);
            activeUserSessions.put(user.getUserId(), session);
            chatRoomManager.getChatRoom(roomId).registerObserver(session);

            // Start polling for messages in a separate thread
            messagePollingExecutor.submit(session::startPolling);

            System.out.println("Successfully joined room '" + roomId + "' as " + username + "!");
            logger.info("User {} joined room {}", username, roomId);

            enterChatRoom(session); // Enter the chat room interface
        } catch (IllegalArgumentException | ChatRoomException e) {
            System.err.println("Error joining room: " + e.getMessage());
            logger.error("Failed to join room {} for user {}: {}", roomId, username, e.getMessage());
        }
    }

    private void handleLogin() {
        System.out.println("\n--- Login to Session ---");
        String userId = readLine("Enter your User ID (from a previous session): ");

        UserSession session = activeUserSessions.get(userId);
        if (session != null && session.getUser().isActive()) {
            // Re-establish connection and restart polling for the session
            session.reconnectAndStartPolling();
            // Re-register as observer to the chat room
            ChatRoom room = chatRoomManager.getChatRoom(session.getRoomId());
            if (room != null) {
                room.registerObserver(session);
                // Also re-add user to the room's active users if they were removed
                // This handles cases where the user left the room but not the session
                if (room.getUser(session.getUser().getUserId()) == null) {
                    try {
                        room.addUser(session.getUser());
                    } catch (ChatRoomException e) {
                        logger.error("Error re-adding user {} to room {}: {}", session.getUser().getUsername(), session.getRoomId(), e.getMessage());
                        System.err.println("Error re-joining room: " + e.getMessage());
                        return;
                    }
                }
            }

            System.out.println("Welcome back, " + session.getUser().getUsername() + "!");
            enterChatRoom(session);
        } else {
            System.out.println("No active session found for User ID: " + userId);
            logger.warn("Failed login attempt for User ID: {}", userId);
        }
    }

    private void handleLogout() {
        System.out.println("\n--- Logout from Session ---");
        String userId = readLine("Enter your User ID to logout: ");

        UserSession session = activeUserSessions.get(userId); // Get session, but don't remove yet
        if (session != null) {
            // First, ensure the user leaves the room if they are currently in one
            ChatRoom room = chatRoomManager.getChatRoom(session.getRoomId());
            if (room != null) {
                room.removeObserver(session); // Unregister observer
                chatRoomManager.leaveChatRoom(session.getRoomId(), session.getUser().getUserId()); // Remove from room
            }
            session.stopPolling(); // Stop the polling thread
            session.getUser().setActive(false); // Mark user as inactive
            activeUserSessions.remove(userId); // Finally, remove the session from the map

            System.out.println("User " + session.getUser().getUsername() + " logged out successfully.");
            logger.info("User {} logged out and session removed.", session.getUser().getUsername());
        } else {
            System.out.println("No active session found for User ID: " + userId);
            logger.warn("Failed logout attempt for User ID: {}", userId);
        }
    }

    private void displayAllRooms() {
        System.out.println("\n--- All Active Chat Rooms ---");
        List<ChatRoom> rooms = chatRoomManager.getAllChatRooms();
        if (rooms.isEmpty()) {
            System.out.println("No chat rooms active.");
            return;
        }
        for (ChatRoom room : rooms) {
            System.out.println("ID: " + room.getRoomId() + ", Name: " + room.getRoomName() + ", Users: " + room.getUserCount());
        }
    }

    private void enterChatRoom(UserSession session) {
        ChatRoom currentRoom = chatRoomManager.getChatRoom(session.getRoomId());
        if (currentRoom == null) {
            System.err.println("Error: Chat room no longer exists.");
            activeUserSessions.remove(session.getUser().getUserId());
            return;
        }

        System.out.println("\n--- Welcome to Room: " + currentRoom.getRoomName() + " (ID: " + currentRoom.getRoomId() + ") ---");
        System.out.println("Your User ID: " + session.getUser().getUserId());
        System.out.println("Type your message and press Enter to send.");
        System.out.println("Type '/users' to see active users.");
        System.out.println("Type '/history' to see message history.");
        System.out.println("Type '/msg <username> <message>' for private message.");
        System.out.println("Type '/leave' to leave the room.");

        // Display initial history
        System.out.println("\n--- Message History ---");
        currentRoom.getMessageHistory().forEach(msg -> System.out.println(msg.getFormattedMessage()));
        System.out.println("-----------------------");

        while (session.getUser().isActive()) {
            String input = readLine("[" + session.getUser().getUsername() + " in " + currentRoom.getRoomName() + "]> ");
            if (input == null) { // EOF, user closed console
                break;
            }

            if (input.trim().isEmpty()) {
                continue;
            }

            try {
                if (input.equalsIgnoreCase("/leave")) {
                    chatRoomManager.leaveChatRoom(session.getRoomId(), session.getUser().getUserId());
                    currentRoom.removeObserver(session);
                    session.stopPolling();
                    // Do NOT remove from activeUserSessions or set user inactive here
                    System.out.println("You have left the room.");
                    logger.info("User {} left room {}", session.getUser().getUsername(), session.getRoomId());
                    break;
                } else if (input.equalsIgnoreCase("/users")) {
                    System.out.println("\n--- Active Users in " + currentRoom.getRoomName() + " ---");
                    currentRoom.getActiveUsers().forEach(user -> System.out.println("- " + user.getUsername() + (user.getUserId().equals(session.getUser().getUserId()) ? " (You)" : "")));
                    System.out.println("-----------------------");
                } else if (input.equalsIgnoreCase("/history")) {
                    System.out.println("\n--- Message History ---");
                    currentRoom.getMessageHistory().forEach(msg -> System.out.println(msg.getFormattedMessage()));
                    System.out.println("-----------------------");
                } else if (input.startsWith("/msg ")) {
                    handlePrivateMessage(session, input);
                }
                else {
                    Message message = new Message(session.getUser().getUserId(), session.getUser().getUsername(), input, session.getRoomId());
                    chatRoomManager.postMessage(session.getRoomId(), message);
                }
            } catch (ChatRoomException | IllegalArgumentException e) {
                System.err.println("Error sending message: " + e.getMessage());
                logger.error("User {} failed to send message in room {}: {}", session.getUser().getUsername(), session.getRoomId(), e.getMessage());
            }
        }
    }

    /**
     * Handles sending a private message from one user to another.
     * Format: /msg <recipient_username> <message>
     * @param senderSession The session of the user sending the private message
     * @param input The raw input string containing the private message command
     */
    private void handlePrivateMessage(UserSession senderSession, String input) {
        String[] parts = input.split(" ", 3); // Split into /msg, username, message
        if (parts.length < 3) {
            System.err.println("Invalid private message format. Use: /msg <username> <message>");
            return;
        }

        String recipientUsername = parts[1];
        String privateContent = parts[2];

        // Find the recipient's UserSession by username
        Optional<UserSession> recipientSessionOpt = activeUserSessions.values().stream()
                .filter(s -> s.getUser().getUsername().equalsIgnoreCase(recipientUsername) && s.getUser().isActive())
                .findFirst();

        if (recipientSessionOpt.isEmpty()) {
            System.err.println("User '" + recipientUsername + "' not found or is inactive.");
            logger.warn("Private message failed: Recipient '{}' not found or inactive.", recipientUsername);
            return;
        }

        UserSession recipientSession = recipientSessionOpt.get();
        User sender = senderSession.getUser();
        User recipient = recipientSession.getUser();

        try {
            PrivateMessage privateMessage = new PrivateMessage(
                    sender.getUserId(), sender.getUsername(),
                    recipient.getUserId(), recipient.getUsername(),
                    privateContent
            );

            // Deliver the private message directly to the recipient's session
            recipientSession.onPrivateMessageReceived(privateMessage);
            // Also display to the sender that the message was sent
            senderSession.onPrivateMessageReceived(privateMessage);

            logger.info("Private message sent from {} to {}: {}", sender.getUsername(), recipient.getUsername(), privateContent);
        } catch (IllegalArgumentException e) {
            System.err.println("Error sending private message: " + e.getMessage());
            logger.error("Failed to send private message from {} to {}: {}", sender.getUsername(), recipient.getUsername(), e.getMessage());
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        try {
            return consoleReader.readLine();
        } catch (IOException e) {
            logger.error("Error reading console input: {}", e.getMessage(), e);
            return null;
        }
    }

    private void shutdown() {
        logger.info("Shutting down application...");
        messagePollingExecutor.shutdownNow(); // Interrupt all polling tasks
        try {
            if (!messagePollingExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                logger.warn("Message polling executor did not terminate in time.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Shutdown interrupted: {}", e.getMessage(), e);
        }

        // Disconnect all active user sessions
        activeUserSessions.values().forEach(session -> {
            session.stopPolling();
            ChatRoom room = chatRoomManager.getChatRoom(session.getRoomId());
            if (room != null) {
                room.removeObserver(session);
                chatRoomManager.leaveChatRoom(session.getRoomId(), session.getUser().getUserId());
            }
        });

        try {
            consoleReader.close();
        } catch (IOException e) {
            logger.error("Error closing console reader: {}", e.getMessage(), e);
        }
        logger.info("Application shutdown complete.");
    }

    /**
     * Inner class representing a user's session and acting as a ChatObserver.
     * Each user session has its own communication protocol and polls for
     * messages.
     */
    private class UserSession implements ChatObserver, Runnable {

        private final User user;
        private final String roomId;
        private CommunicationProtocol protocol;
        private volatile boolean pollingActive;
        private final BlockingQueue<Message> incomingMessages;
        private final BlockingQueue<PrivateMessage> incomingPrivateMessages; // For private messages
        private final BlockingQueue<String> notifications; // For user join/leave/error notifications

        public UserSession(User user, String roomId) {
            this.user = user;
            this.roomId = roomId;
            this.pollingActive = true;
            this.incomingMessages = new LinkedBlockingQueue<>();
            this.incomingPrivateMessages = new LinkedBlockingQueue<>(); // Initialize private message queue
            this.notifications = new LinkedBlockingQueue<>();
            initializeProtocol();
        }

        private void initializeProtocol() {
            // For demonstration, let's randomly pick a protocol
            // In a real application, this would be determined by client type
            int choice = new Random().nextInt(3);
            switch (choice) {
                case 0:
                    this.protocol = new WebSocketProtocol("ws://localhost:8080/chat");
                    break;
                case 1:
                    this.protocol = new HttpProtocol("http://localhost:8081/chat");
                    break;
                case 2:
                    this.protocol = new TcpProtocol("localhost", 8082);
                    break;
            }
            this.protocol.connect();
            logger.info("User {} session initialized with {} protocol.", user.getUsername(), protocol.getProtocolName());
        }

        public User getUser() {
            return user;
        }

        public String getRoomId() {
            return roomId;
        }

        @Override
        public String getObserverUserId() {
            return user.getUserId();
        }

        @Override
        public void onMessageReceived(Message message) {
            if (!message.getSenderId().equals(user.getUserId())) { // Don't notify self
                incomingMessages.offer(message);
            }
        }

        @Override
        public void onUserJoined(User joinedUser) {
            if (!joinedUser.getUserId().equals(user.getUserId())) { // Don't notify self
                notifications.offer(joinedUser.getUsername() + " has joined the room.");
            }
        }

        @Override
        public void onUserLeft(User leftUser) {
            if (!leftUser.getUserId().equals(user.getUserId())) { // Don't notify self
                notifications.offer(leftUser.getUsername() + " has left the room.");
            }
        }

        @Override
        public void onError(String errorMessage) {
            notifications.offer("Error: " + errorMessage);
        }

        @Override
        public void onPrivateMessageReceived(PrivateMessage privateMessage) {
            incomingPrivateMessages.offer(privateMessage);
        }

        public void startPolling() {
            pollingActive = true;
            run(); // Start the polling loop
        }

        public void stopPolling() {
            pollingActive = false;
            if (protocol != null) {
                protocol.disconnect();
            }
            logger.info("User {} session polling stopped.", user.getUsername());
        }

        /**
         * Reconnects the protocol and restarts the message polling for the
         * session.
         */
        public void reconnectAndStartPolling() {
            if (protocol != null && !protocol.isConnected()) {
                protocol.connect();
                logger.info("User {} session protocol reconnected.", user.getUsername());
            }
            if (!pollingActive) {
                pollingActive = true;
                messagePollingExecutor.submit(this); // Resubmit the polling task
                logger.info("User {} session polling restarted.", user.getUsername());
            }
        }

        @Override
        public void run() {
            while (pollingActive && protocol.isConnected()) {
                try {
                    // Process incoming messages from the chat room (via Observer pattern)
                    Message msg = incomingMessages.poll(100, TimeUnit.MILLISECONDS);
                    if (msg != null) {
                        System.out.println("\n" + msg.getFormattedMessage());
                        System.out.print("[" + user.getUsername() + " in " + roomId + "]> ");
                        System.out.flush(); // Ensure prompt is reprinted
                    }

                    // Process notifications
                    String notification = notifications.poll(100, TimeUnit.MILLISECONDS);
                    if (notification != null) {
                        System.out.println("\n[NOTIFICATION]: " + notification);
                        System.out.print("[" + user.getUsername() + " in " + roomId + "]> ");
                        System.out.flush();
                    }

                    // Process incoming private messages
                    PrivateMessage privateMsg = incomingPrivateMessages.poll(100, TimeUnit.MILLISECONDS);
                    if (privateMsg != null) {
                        System.out.println("\n" + privateMsg.getFormattedMessage());
                        System.out.print("[" + user.getUsername() + " in " + roomId + "]> ");
                        System.out.flush();
                    }

                    // Simulate receiving messages via the chosen protocol (if applicable)
                    // For this console app, the primary message flow is via the Observer pattern.
                    // The protocol's receiveMessage is more for external client simulation.
                    // Message protocolMessage = protocol.receiveMessage();
                    // if (protocolMessage != null) {
                    //     System.out.println("\n[PROTOCOL MESSAGE]: " + protocolMessage.getFormattedMessage());
                    //     System.out.print("[" + user.getUsername() + " in " + roomId + "]> ");
                    //     System.out.flush();
                    // }
                    Thread.sleep(200); // Poll every 200ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.warn("User {} message polling interrupted.", user.getUsername());
                    pollingActive = false;
                } catch (Exception e) {
                    logger.error("Error during message polling for user {}: {}", user.getUsername(), e.getMessage(), e);
                    pollingActive = false;
                }
            }
            logger.info("User {} polling loop exited.", user.getUsername());
        }
    }
}
