package ru.tinkoff.edu.java.link_parser;

public final class StackOverFlowLinkParser implements LinkParser {
    public ParsingResult parse(String link) {
        var splitted = link.split("/+");
        if (splitted.length < 4 || !"stackoverflow.com".equals(splitted[1])) {
            return null;
        }
        return new ParsingResult(splitted[3]);
    }
}
