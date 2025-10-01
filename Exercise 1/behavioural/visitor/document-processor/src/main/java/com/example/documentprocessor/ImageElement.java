package com.example.documentprocessor;

public class ImageElement implements DocumentElement {
    private String url;
    private String altText;

    public ImageElement(String url, String altText) {
        this.url = url;
        this.altText = altText;
    }

    public String getUrl() {
        return url;
    }

    public String getAltText() {
        return altText;
    }

    @Override
    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}
