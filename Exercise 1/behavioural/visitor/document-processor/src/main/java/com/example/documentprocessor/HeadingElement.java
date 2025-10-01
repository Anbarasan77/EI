package com.example.documentprocessor;

public class HeadingElement implements DocumentElement {
    private String text;
    private int level;

    public HeadingElement(String text, int level) {
        this.text = text;
        this.level = level;
    }

    public String getText() {
        return text;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}
