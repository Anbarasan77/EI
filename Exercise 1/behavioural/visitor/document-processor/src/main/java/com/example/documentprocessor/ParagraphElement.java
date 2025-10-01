package com.example.documentprocessor;

public class ParagraphElement implements DocumentElement {
    private String text;

    public ParagraphElement(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}
