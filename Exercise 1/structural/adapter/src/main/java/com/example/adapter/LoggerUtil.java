package com.example.adapter;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {
    private static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());
    private static FileHandler fh;

    static {
        try {
            // Configure the logger to write to a file
            fh = new FileHandler("payment_system.log", true); // true for append mode
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            logger.setLevel(Level.INFO); // Set default logging level
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error setting up logger file handler", e);
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
