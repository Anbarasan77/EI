package com.prototype.document;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * The DocumentRegistry class acts as a cache for prototype documents.
 * It allows registration and retrieval of documents by type, facilitating cloning.
 */
public class DocumentRegistry {
    private static final Logger LOGGER = Logger.getLogger(DocumentRegistry.class.getName());
    private Map<String, Document> prototypes = new HashMap<>();

    public DocumentRegistry() {
        LOGGER.log(Level.INFO, "DocumentRegistry initialized.");
    }

    /**
     * Registers a prototype document with a given key.
     *
     * @param key The unique key for the document type (e.g., "Report", "Presentation").
     * @param document The prototype document instance.
     */
    public void registerDocument(String key, Document document) {
        if (key == null || key.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to register document with an empty or null key.");
            throw new IllegalArgumentException("Document key cannot be empty or null.");
        }
        if (document == null) {
            LOGGER.log(Level.WARNING, "Attempted to register a null document for key: {0}", key);
            throw new IllegalArgumentException("Document cannot be null.");
        }
        prototypes.put(key, document);
        LOGGER.log(Level.INFO, "Document prototype registered: {0}", key);
    }

    /**
     * Retrieves a cloned document based on the registered prototype.
     *
     * @param key The key of the prototype document to clone.
     * @return A new cloned Document instance.
     * @throws CloneNotSupportedException if the prototype document cannot be cloned.
     * @throws IllegalArgumentException if no prototype is found for the given key.
     */
    public Document getClonedDocument(String key) throws CloneNotSupportedException {
        if (key == null || key.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Attempted to get cloned document with an empty or null key.");
            throw new IllegalArgumentException("Document key cannot be empty or null.");
        }
        Document prototype = prototypes.get(key);
        if (prototype == null) {
            LOGGER.log(Level.WARNING, "No document prototype found for key: {0}", key);
            throw new IllegalArgumentException("No document prototype found for key: " + key);
        }
        LOGGER.log(Level.INFO, "Cloning document prototype for key: {0}", key);
        return prototype.cloneDocument();
    }

    /**
     * Checks if a document prototype exists for a given key.
     * @param key The key to check.
     * @return true if a prototype exists, false otherwise.
     */
    public boolean containsDocument(String key) {
        return prototypes.containsKey(key);
    }
}
