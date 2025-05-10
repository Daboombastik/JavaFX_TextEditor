//package fr.shcherbakov.javafxtexteditor.controller;
//
//import org.fxmisc.richtext.CodeArea;
//import org.fxmisc.richtext.LineNumberFactory;
//import org.fxmisc.richtext.model.StyleSpans;
//import org.fxmisc.richtext.model.StyleSpansBuilder;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class CodeController {
//
//
//    private CodeArea codeArea;
//
//    private void setupSyntaxHighlighting() {
//        codeArea = new CodeArea();
//        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
//
//        codeArea.richChanges()
////                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // only if text changed
//                .subscribe(change -> codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText())));
//
//        codeArea.replaceText("public class Hello {}"); // sample text
////        stackPane.getChildren().add(codeArea); // instead of textArea
//    }
//
//    private static final String[] KEYWORDS = new String[]{
//            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
//            "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
//            "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
//            "interface", "long", "native", "new", "package", "private", "protected", "public",
//            "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
//            "throw", "throws", "transient", "try", "void", "volatile", "while"
//    };
//
//    private static final Pattern PATTERN = Pattern.compile("\\b(" + String.join("|", KEYWORDS) + ")\\b");
//
//    private StyleSpans<Collection<String>> computeHighlighting(String text) {
//        Matcher matcher = PATTERN.matcher(text);
//        int lastKwEnd = 0;
//        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
//        while (matcher.find()) {
//            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
//            spansBuilder.add(Collections.singleton("keyword"), matcher.end() - matcher.start());
//            lastKwEnd = matcher.end();
//        }
//        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
//        return spansBuilder.create();
//    }
//}