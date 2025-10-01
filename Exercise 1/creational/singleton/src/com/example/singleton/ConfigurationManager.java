package com.example.singleton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigurationManager {
    private static final Logger LOGGER = Logger.getLogger(ConfigurationManager.class.getName());
    private static ConfigurationManager instance;
    private Properties properties;

    // Private constructor to prevent instantiation
    private ConfigurationManager() {
        properties = new Properties();
        loadProperties();
    }

    // Public method to provide the single instance
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                LOGGER.log(Level.WARNING, "Sorry, unable to find application.properties. Using default empty properties.");
                return;
            }
            properties.load(input);
            LOGGER.log(Level.INFO, "application.properties loaded successfully.");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error loading configuration properties: " + ex.getMessage(), ex);
            // In a real application, you might want to throw a custom runtime exception
            // or ensure default values are set if configuration is critical.
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "An unexpected error occurred during configuration loading: " + ex.getMessage(), ex);
        }
    }

    public String getProperty(String key) {
        // Defensive programming: validate input key
        if (key == null || key.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to retrieve property with a null or empty key.");
            return null;
        }
        String value = properties.getProperty(key);
        if (value == null) {
            LOGGER.log(Level.INFO, "Property '" + key + "' not found in configuration.");
        }
        return value;
    }

    public String getProperty(String key, String defaultValue) {
        // Defensive programming: validate input key
        if (key == null || key.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to retrieve property with a null or empty key. Returning default value.");
            return defaultValue;
        }
        return properties.getProperty(key, defaultValue);
    }

    // For demonstration/testing purposes, allow reloading properties (optional)
    public synchronized void reloadConfiguration() {
        LOGGER.log(Level.INFO, "Reloading configuration properties.");
        properties = new Properties(); // Clear existing properties
        loadProperties();
    }
}
