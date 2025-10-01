# Singleton Design Pattern Demonstration: Configuration Manager

This project demonstrates the **Singleton design pattern** in Java using a `ConfigurationManager` as a practical use case. The Singleton pattern ensures that a class has only one instance and provides a global point of access to that instance.

## Design Pattern: Singleton

The Singleton pattern is a creational design pattern that restricts the instantiation of a class to a single object. This is useful when exactly one object is needed to coordinate actions across the system.

In this project, the `ConfigurationManager` is implemented as a Singleton. This guarantees that all parts of the application access the same configuration settings, preventing inconsistencies and redundant resource loading.

## Use Case: Configuration Manager

A `ConfigurationManager` is an ideal candidate for the Singleton pattern because:
*   **Single Source of Truth:** All application components should retrieve configuration settings from a single, consistent source.
*   **Resource Efficiency:** Configuration files (like `application.properties`) typically need to be loaded only once at application startup. Creating multiple instances of a configuration manager would lead to unnecessary file I/O and memory consumption.
*   **Global Access:** Configuration settings are often needed across various modules and layers of an application, requiring a globally accessible instance.

## Features & Best Practices

This implementation adheres to several best practices and includes:

*   **Clear Code Organization:** Each class (`ConfigurationManager`, `Main`) resides in its own file within a logical package structure (`com.example.singleton`).
*   **Consistent Naming Conventions:** Standard Java naming conventions are followed for classes, methods, and variables.
*   **Robust Logging:** Utilizes `java.util.logging.Logger` to provide informative messages about application flow, configuration loading, and potential issues.
*   **Comprehensive Exception Handling:** The `ConfigurationManager` gracefully handles `IOException` during property file loading and logs severe errors.
*   **Defensive Programming:** Methods like `getProperty` include checks for `null` or empty input keys, preventing `NullPointerExceptions` and ensuring reliable access to properties.
*   **Lazy Initialization:** The Singleton instance is created only when it's first requested, optimizing startup performance.
*   **Thread Safety:** The `getInstance()` method is `synchronized` to ensure thread-safe instantiation of the Singleton.

## Project Structure

```
.
├── bin/                                 # Compiled Java classes and copied resources
│   ├── application.properties
│   └── com/example/singleton/
│       ├── ConfigurationManager.class
│       └── Main.class
├── src/
│   ├── com/example/singleton/
│   │   ├── ConfigurationManager.java    # Singleton class for managing configurations
│   │   └── Main.java                    # Main application to demonstrate Singleton usage
│   └── main/resources/
│       └── application.properties       # Configuration file
└── README.md                            # This file
```

## How to Run the Application

To compile and run this Java application, follow these steps:

1.  **Navigate to the project root:**
    ```bash
    cd c:\EI\Exercise 1\creational\singleton
    ```

2.  **Compile the Java source files:**
    This command compiles all `.java` files in the `src/com/example/singleton` directory and places the compiled `.class` files into a `bin` directory.
    ```bash
    javac -d bin src/com/example/singleton/*.java
    ```

3.  **Copy the `application.properties` file:**
    The `ConfigurationManager` expects `application.properties` to be on the classpath. Copy it into the `bin` directory alongside the compiled classes.
    ```bash
    copy src\main\resources\application.properties bin
    ```

4.  **Run the application:**
    Execute the `Main` class, ensuring the `bin` directory is on the classpath.
    ```bash
    java -cp bin com.example.singleton.Main
    ```

## Expected Output

Upon running the application, you will see console output similar to this:

```
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: --- Starting Singleton Configuration Manager Demo ---
Oct 01, 2025 1:14:23 PM com.example.singleton.ConfigurationManager loadProperties
INFO: application.properties loaded successfully.
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: ConfigurationManager instance 1 obtained.
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: ConfigurationManager instance 2 obtained.
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: Both config1 and config2 refer to the same instance. Singleton pattern works!
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO:
--- Accessing Configuration Properties ---
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: Application Name: SingletonDemoApp
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: Application Version: 1.0.0
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: Database URL: jdbc:mysql://localhost:3306/mydb
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: Database User: admin
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: Feature Enabled: true
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: Non-existent property (with default): DEFAULT_VALUE
Oct 01, 2025 1:14:23 PM com.example.singleton.ConfigurationManager getProperty
INFO: Property 'another.missing.key' not found in configuration.
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: Non-existent property (no default): null
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO:
--- Demonstrating Defensive Programming ---
Oct 01, 2025 1:14:23 PM com.example.singleton.ConfigurationManager getProperty
WARNING: Attempted to retrieve property with a null or empty key. Returning default value.
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: Property with null key: NullKeyDefault
Oct 01, 2025 1:14:23 PM com.example.singleton.ConfigurationManager getProperty
WARNING: Attempted to retrieve property with a null or empty key. Returning default value.
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: Property with empty key: EmptyKeyDefault
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO:
--- Demonstrating Configuration Reload (Optional) ---
Oct 01, 2025 1:14:23 PM com.example.singleton.ConfigurationManager reloadConfiguration
INFO: Reloading configuration properties.
Oct 01, 2025 1:14:23 PM com.example.singleton.ConfigurationManager loadProperties
INFO: application.properties loaded successfully.
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: Application Name after reload: SingletonDemoApp
Oct 01, 2025 1:14:23 PM com.example.singleton.Main main
INFO: --- Singleton Configuration Manager Demo Finished ---
```

The line `Both config1 and config2 refer to the same instance. Singleton pattern works!` explicitly confirms that the `ConfigurationManager` is indeed a Singleton, as both variables point to the identical object in memory. The configuration properties are consistently retrieved from this single, globally accessible instance.
