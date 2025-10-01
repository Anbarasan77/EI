package com.example.documentprocessor;

public class HtmlExportVisitor implements DocumentVisitor {
    private StringBuilder htmlOutput = new StringBuilder();

    @Override
    public void visit(HeadingElement heading) {
        htmlOutput.append(String.format("<h%d>%s</h%d>\n", heading.getLevel(), heading.getText(), heading.getLevel()));
    }

    @Override
    public void visit(ParagraphElement paragraph) {
        htmlOutput.append(String.format("<p>%s</p>\n", paragraph.getText()));
    }

    @Override
    public void visit(ImageElement image) {
        htmlOutput.append(String.format("<img src=\"%s\" alt=\"%s\"/>\n", image.getUrl(), image.getAltText()));
    }

    public String getHtmlOutput() {
        return htmlOutput.toString();
    }
}
