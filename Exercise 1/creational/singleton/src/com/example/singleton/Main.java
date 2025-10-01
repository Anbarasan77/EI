package com.example.singleton;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Configure logging to show INFO messages
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);
        // Optional: if you want to remove console handler and add your own
        // for (java.util.logging.Handler handler : rootLogger.getHandlers()) {
        //     rootLogger.removeHandler(handler);
        // }
        // rootLogger.addHandler(new java.util.logging.ConsoleHandler());


        LOGGER.log(Level.INFO, "--- Starting Singleton Configuration Manager Demo ---");

        // Get the first instance of ConfigurationManager
        ConfigurationManager config1 = ConfigurationManager.getInstance();
        LOGGER.log(Level.INFO, "ConfigurationManager instance 1 obtained.");

        // Get the second instance of ConfigurationManager
        ConfigurationManager config2 = ConfigurationManager.getInstance();
        LOGGER.log(Level.INFO, "ConfigurationManager instance 2 obtained.");

        // Verify that both instances are the same
        if (config1 == config2) {
            LOGGER.log(Level.INFO, "Both config1 and config2 refer to the same instance. Singleton pattern works!");
        } else {
            LOGGER.log(Level.SEVERE, "Error: config1 and config2 are different instances. Singleton pattern failed!");
        }

        // Access configuration properties
        LOGGER.log(Level.INFO, "\n--- Accessing Configuration Properties ---");
        LOGGER.log(Level.INFO, "Application Name: " + config1.getProperty("app.name", "DefaultApp"));
        LOGGER.log(Level.INFO, "Application Version: " + config1.getProperty("app.version", "N/A"));
        LOGGER.log(Level.INFO, "Database URL: " + config1.getProperty("database.url"));
        LOGGER.log(Level.INFO, "Database User: " + config1.getProperty("database.user"));
        LOGGER.log(Level.INFO, "Feature Enabled: " + config1.getProperty("feature.enabled"));
        LOGGER.log(Level.INFO, "Non-existent property (with default): " + config1.getProperty("non.existent.key", "DEFAULT_VALUE"));
        LOGGER.log(Level.INFO, "Non-existent property (no default): " + config1.getProperty("another.missing.key"));

        // Demonstrate defensive programming for invalid keys
        LOGGER.log(Level.INFO, "\n--- Demonstrating Defensive Programming ---");
        LOGGER.log(Level.INFO, "Property with null key: " + config1.getProperty(null, "NullKeyDefault"));
        LOGGER.log(Level.INFO, "Property with empty key: " + config1.getProperty(" ", "EmptyKeyDefault"));

        // Demonstrate reloading configuration (optional feature)
        LOGGER.log(Level.INFO, "\n--- Demonstrating Configuration Reload (Optional) ---");
        // In a real scenario, this might be triggered by an external event or admin command
        config1.reloadConfiguration();
        LOGGER.log(Level.INFO, "Application Name after reload: " + config1.getProperty("app.name", "DefaultApp"));


        LOGGER.log(Level.INFO, "--- Singleton Configuration Manager Demo Finished ---");
    }
}
