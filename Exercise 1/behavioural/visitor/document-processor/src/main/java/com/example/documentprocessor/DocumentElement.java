package com.example.documentprocessor;

public interface DocumentElement {
    void accept(DocumentVisitor visitor);
}
