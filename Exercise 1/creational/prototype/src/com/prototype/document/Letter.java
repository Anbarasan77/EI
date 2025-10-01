package com.prototype.document;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Concrete implementation of a Letter document.
 */
public class Letter implements Document {
    private static final Logger LOGGER = Logger.getLogger(Letter.class.getName());

    private String title;
    private String content;
    private String recipient;
    private String sender;

    public Letter(String title, String content, String recipient, String sender) {
        this.title = title;
        this.content = content;
        this.recipient = recipient;
        this.sender = sender;
        LOGGER.log(Level.INFO, "Letter created: {0}", title);
    }

    // Copy constructor for cloning
    private Letter(Letter source) {
        this.title = source.title;
        this.content = source.content;
        this.recipient = source.recipient;
        this.sender = source.sender;
        LOGGER.log(Level.INFO, "Letter cloned: {0}", title);
    }

    @Override
    public Document cloneDocument() throws CloneNotSupportedException {
        return new Letter(this);
    }

    @Override
    public void displayDocument() {
        LOGGER.log(Level.INFO, "Displaying Letter: {0}", title);
        System.out.println("--- Letter ---");
        System.out.println("Title: " + title);
        System.out.println("Recipient: " + recipient);
        System.out.println("Sender: " + sender);
        System.out.println("Content: " + content);
        System.out.println("--------------");
    }

    @Override
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to set an empty or null title for Letter.");
            throw new IllegalArgumentException("Title cannot be empty or null.");
        }
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to set empty or null content for Letter.");
            throw new IllegalArgumentException("Content cannot be empty or null.");
        }
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        if (recipient == null || recipient.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to set an empty or null recipient for Letter.");
            throw new IllegalArgumentException("Recipient cannot be empty or null.");
        }
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        if (sender == null || sender.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to set an empty or null sender for Letter.");
            throw new IllegalArgumentException("Sender cannot be empty or null.");
        }
        this.sender = sender;
    }
}
