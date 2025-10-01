# EI 2025-26 - Placement Drive

This repository contains solutions and demonstrations for two exercises:

1.  **Design Patterns Problem Statement**: Demonstrating understanding of various software design patterns through creative use cases.
2.  **Real-time Chat Application Mini-project**: Building a real-time chat application applying specific design patterns and OOP principles.

---

## Exercise 1: Problem Statement on Design Patterns

This exercise focuses on demonstrating an understanding of fundamental software design patterns by implementing six distinct use cases. For each of the following categories, two unique use cases have been developed and coded:

### 1. Behavioral Design Patterns

- **Objective**: To illustrate how objects interact and distribute responsibilities.
- **Demonstrations**:
  - **Observer Pattern**: [Brief description of the use case implemented, e.g., "A weather station system where displays automatically update when weather data changes."]
  - **Visitor Pattern**: [Brief description of the use case implemented, e.g., "A document processing system that applies different operations (e.g., XML export, plain text export) to various document elements (e.g., paragraphs, headers, images) without modifying their classes."]

### 2. Creational Design Patterns

- **Objective**: To illustrate how objects are instantiated, providing flexibility and reusability.
- **Demonstrations**:
  - **Prototype Pattern**: [Brief description of the use case implemented, e.g., "A document management system that creates new documents by cloning existing document templates (e.g., letters, reports, presentations) to avoid costly re-initialization."]
  - **Singleton Pattern**: [Brief description of the use case implemented, e.g., "A configuration manager that ensures only one instance of the configuration settings is accessible throughout the application, preventing inconsistencies."]

### 3. Structural Design Patterns

- **Objective**: To illustrate how objects and classes are composed to form larger structures.
- **Demonstrations**:
  - **Adapter Pattern**: [Brief description of the use case implemented, e.g., "A payment processing system that integrates with multiple third-party payment gateways (e.g., PayPal, Stripe) by adapting their incompatible interfaces to a common payment interface."]
  - **Bridge Pattern**: [Brief description of the use case implemented, e.g., "A notification system that can send various types of notifications (e.g., email, SMS, push) using different sending mechanisms, allowing independent variation of notification types and senders."]

---

## Exercise 2: Problem Statements for Mini-projects - Real-time Chat Application

This mini-project involves creating a simple real-time chat application that allows users to communicate within various chat rooms.

### Functional Requirements:

1.  **Chat Room Management**: Users can create new chat rooms or join existing ones by providing a unique room ID.
2.  **Real-time Messaging**: Users can send and receive messages instantly within their joined chat rooms.
3.  **Active User List**: The application displays a list of all active users currently present in a chat room.
4.  **Optional: Private Messaging**: (If implemented) Users can send private messages to other individual users.
5.  **Optional: Message History**: (If implemented) Chat messages persist, allowing users to view past conversations even after leaving and rejoining a room.

### Key Focus Areas:

- **Behavioral Pattern - Observer Pattern**: Utilized to notify all subscribed clients (users) in a chat room about new messages or changes in user activity (e.g., a user joining or leaving).
- **Creational Pattern - Singleton Pattern**: Employed to manage the central state of all chat rooms, ensuring that there is only one instance responsible for creating, retrieving, and managing chat rooms across the application.
- **Structural Pattern - Adapter Pattern**: Used to enable the chat system to communicate with clients using different communication protocols (e.g., WebSockets, HTTP, TCP) through a unified interface, promoting flexibility and extensibility.
- **Object-Oriented Programming (OOP) Principles**: Effective application of encapsulation, polymorphism, and inheritance throughout the codebase to ensure a robust, maintainable, and scalable design.
