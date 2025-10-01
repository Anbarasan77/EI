package com.prototype.document;

/**
 * The Document interface declares the cloning method.
 * All concrete document types must implement this interface.
 */
public interface Document extends Cloneable {

    /**
     * Creates a new object that is a copy of the current instance.
     *
     * @return A new object that is a copy of this instance.
     * @throws CloneNotSupportedException if the object's class does not support the Cloneable interface.
     */
    Document cloneDocument() throws CloneNotSupportedException;

    /**
     * Displays the details of the document.
     */
    void displayDocument();

    /**
     * Sets the title of the document.
     * @param title The new title for the document.
     */
    void setTitle(String title);

    /**
     * Gets the title of the document.
     * @return The title of the document.
     */
    String getTitle();

    /**
     * Sets the content of the document.
     * @param content The new content for the document.
     */
    void setContent(String content);

    /**
     * Gets the content of the document.
     * @return The content of the document.
     */
    String getContent();
}
