package ru.tinkoff.edu.java.link_parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GitHubLinkParserTest {
    private final GitHubLinkParser parser = new GitHubLinkParser();

    @Test
    public void validLinks() {
        assertAll(
                () -> assertNotNull(parser.parse("https://github.com/sanyarnd/tinkoff-java-course-2022/")),
                () -> assertNotNull(parser.parse("https://github.com/sanyarnd/tinkoff-java-course-2022")),
                () -> assertNotNull(parser.parse("https://github.com/sanyarnd/tinkoff-java-course-2022/abc")));
    }

    @Test
    public void invalidLinks() {
        assertAll(
                () -> assertNull(parser.parse("https://github.com/sanyarnd/")),
                () -> assertNull(parser.parse("https//github.com/sanyarnd/tinkoff-java-course-2022")),
                () -> assertNull(parser.parse("https://github.co/sanyarnd/tinkoff-java-course-2022/abc")));
    }
}
