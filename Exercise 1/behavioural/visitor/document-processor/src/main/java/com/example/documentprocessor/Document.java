package com.example.documentprocessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Document {
    private final List<DocumentElement> elements = new ArrayList<>();

    public void addElement(DocumentElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Document element cannot be null.");
        }
        this.elements.add(element);
    }

    public List<DocumentElement> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public void accept(DocumentVisitor visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException("Document visitor cannot be null.");
        }
        for (DocumentElement element : elements) {
            element.accept(visitor);
        }
    }
}
