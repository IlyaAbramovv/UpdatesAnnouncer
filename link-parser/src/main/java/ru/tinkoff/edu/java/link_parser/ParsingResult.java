package ru.tinkoff.edu.java.link_parser;

import java.util.List;

public class ParsingResult {
    final List<String> result;

    public ParsingResult(String... parsed) {
        result = List.of(parsed);
    }
}
