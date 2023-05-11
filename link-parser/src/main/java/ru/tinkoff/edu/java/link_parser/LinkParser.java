package ru.tinkoff.edu.java.link_parser;

public sealed interface LinkParser permits GitHubLinkParser, StackOverFlowLinkParser {
    ParsingResult parse(String link);
}
