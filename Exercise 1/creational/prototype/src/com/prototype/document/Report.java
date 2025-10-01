package com.prototype.document;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Concrete implementation of a Report document.
 */
public class Report implements Document {
    private static final Logger LOGGER = Logger.getLogger(Report.class.getName());

    private String title;
    private String content;
    private String author;
    private String date;

    public Report(String title, String content, String author, String date) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
        LOGGER.log(Level.INFO, "Report created: {0}", title);
    }

    // Copy constructor for cloning
    private Report(Report source) {
        this.title = source.title;
        this.content = source.content;
        this.author = source.author;
        this.date = source.date;
        LOGGER.log(Level.INFO, "Report cloned: {0}", title);
    }

    @Override
    public Document cloneDocument() throws CloneNotSupportedException {
        // Deep copy if there were mutable objects, but for strings, shallow copy is fine.
        return new Report(this);
    }

    @Override
    public void displayDocument() {
        LOGGER.log(Level.INFO, "Displaying Report: {0}", title);
        System.out.println("--- Report ---");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Date: " + date);
        System.out.println("Content: " + content);
        System.out.println("--------------");
    }

    @Override
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to set an empty or null title for Report.");
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
            LOGGER.log(Level.WARNING, "Attempted to set empty or null content for Report.");
            throw new IllegalArgumentException("Content cannot be empty or null.");
        }
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to set an empty or null author for Report.");
            throw new IllegalArgumentException("Author cannot be empty or null.");
        }
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to set an empty or null date for Report.");
            throw new IllegalArgumentException("Date cannot be empty or null.");
        }
        this.date = date;
    }
}
