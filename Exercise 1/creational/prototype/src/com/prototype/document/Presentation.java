package com.prototype.document;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Concrete implementation of a Presentation document.
 */
public class Presentation implements Document {
    private static final Logger LOGGER = Logger.getLogger(Presentation.class.getName());

    private String title;
    private String content;
    private int numberOfSlides;
    private String presenter;

    public Presentation(String title, String content, int numberOfSlides, String presenter) {
        this.title = title;
        this.content = content;
        this.numberOfSlides = numberOfSlides;
        this.presenter = presenter;
        LOGGER.log(Level.INFO, "Presentation created: {0}", title);
    }

    // Copy constructor for cloning
    private Presentation(Presentation source) {
        this.title = source.title;
        this.content = source.content;
        this.numberOfSlides = source.numberOfSlides;
        this.presenter = source.presenter;
        LOGGER.log(Level.INFO, "Presentation cloned: {0}", title);
    }

    @Override
    public Document cloneDocument() throws CloneNotSupportedException {
        return new Presentation(this);
    }

    @Override
    public void displayDocument() {
        LOGGER.log(Level.INFO, "Displaying Presentation: {0}", title);
        System.out.println("--- Presentation ---");
        System.out.println("Title: " + title);
        System.out.println("Presenter: " + presenter);
        System.out.println("Number of Slides: " + numberOfSlides);
        System.out.println("Content: " + content);
        System.out.println("--------------------");
    }

    @Override
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to set an empty or null title for Presentation.");
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
            LOGGER.log(Level.WARNING, "Attempted to set empty or null content for Presentation.");
            throw new IllegalArgumentException("Content cannot be empty or null.");
        }
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    public int getNumberOfSlides() {
        return numberOfSlides;
    }

    public void setNumberOfSlides(int numberOfSlides) {
        if (numberOfSlides <= 0) {
            LOGGER.log(Level.WARNING, "Attempted to set non-positive number of slides for Presentation.");
            throw new IllegalArgumentException("Number of slides must be positive.");
        }
        this.numberOfSlides = numberOfSlides;
    }

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        if (presenter == null || presenter.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to set an empty or null presenter for Presentation.");
            throw new IllegalArgumentException("Presenter cannot be empty or null.");
        }
        this.presenter = presenter;
    }
}
