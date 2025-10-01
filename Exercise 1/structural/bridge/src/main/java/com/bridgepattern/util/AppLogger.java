package com.bridgepattern.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * A utility class for application-wide logging.
 * Provides a centralized way to log messages with different severity levels.
 */
public class AppLogger {
    private static final Logger logger = Logger.getLogger(AppLogger.class.getName());
    private static FileHandler fileHandler;

    static {
        try {
            // Configure logger to write to a file
            fileHandler = new FileHandler("app.log", true); // true for append mode
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO); // Default logging level
            logger.info("AppLogger initialized.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize AppLogger file handler.", e);
        }
    }

    /**
     * Logs an informational message.
     * @param message The message to log.
     */
    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    /**
     * Logs a warning message.
     * @param message The message to log.
     */
    public static void warning(String message) {
        logger.log(Level.WARNING, message);
    }

    /**
     * Logs an error message.
     * @param message The message to log.
     * @param throwable An optional Throwable associated with the error.
     */
    public static void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    /**
     * Logs a severe message.
     * @param message The message to log.
     */
    public static void severe(String message) {
        logger.log(Level.SEVERE, message);
    }

    /**
     * Closes the file handler, flushing any buffered output.
     * Should be called when the application is shutting down.
     */
    public static void close() {
        if (fileHandler != null) {
            fileHandler.close();
            logger.removeHandler(fileHandler);
            AppLogger.info("AppLogger closed.");
        }
    }
}
