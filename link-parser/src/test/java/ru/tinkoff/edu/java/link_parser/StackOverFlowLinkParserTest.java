package ru.tinkoff.edu.java.link_parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackOverFlowLinkParserTest {
    private final StackOverFlowLinkParser parser = new StackOverFlowLinkParser();

    @Test
    public void validLinks() {
        assertAll(
                () -> assertNotNull(parser.parse("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c")),
                () -> assertNotNull(parser.parse("https://stackoverflow.com/questions/1642028/")),
                () -> assertNotNull(parser.parse("https://stackoverflow.com/questions/1642028")));
    }

    @Test
    public void invalidLinks() {
        assertAll(
                () -> assertNull(parser.parse("https://stackoverflow.com/")),
                () -> assertNull(parser.parse("https://stackoverflow.co/123")),
                () -> assertNull(parser.parse("stackoverflow.com/123")),
                () -> assertNull(parser.parse("https//stackoverflow.com/questions/1642028/what-is-the-operator-in-c"))
        );
    }

}