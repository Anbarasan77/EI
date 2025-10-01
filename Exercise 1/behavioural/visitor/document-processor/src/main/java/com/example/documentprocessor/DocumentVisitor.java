package com.example.documentprocessor;

public interface DocumentVisitor {
    void visit(HeadingElement heading);
    void visit(ParagraphElement paragraph);
    void visit(ImageElement image);
}
