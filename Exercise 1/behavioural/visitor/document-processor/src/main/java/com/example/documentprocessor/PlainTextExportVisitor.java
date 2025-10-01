package com.example.documentprocessor;

public class PlainTextExportVisitor implements DocumentVisitor {
    private StringBuilder plainTextOutput = new StringBuilder();

    @Override
    public void visit(HeadingElement heading) {
        plainTextOutput.append(heading.getText()).append("\n");
    }

    @Override
    public void visit(ParagraphElement paragraph) {
        plainTextOutput.append(paragraph.getText()).append("\n");
    }

    @Override
    public void visit(ImageElement image) {
        plainTextOutput.append("[Image: ").append(image.getAltText()).append("]\n");
    }

    public String getPlainTextOutput() {
        return plainTextOutput.toString();
    }
}
