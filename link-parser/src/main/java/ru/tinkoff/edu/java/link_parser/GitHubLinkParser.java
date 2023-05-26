package ru.tinkoff.edu.java.link_parser;

public final class GitHubLinkParser implements LinkParser {

    @Override
    public ParsingResult parse(String link) {
        var splitted = link.split("/+");
        if (splitted.length < 4 || !"github.com".equals(splitted[1]) || !"https:".equals(splitted[0])) {
            return null;
        }
        return new ParsingResult(splitted[2], splitted[3]);
    }

}
