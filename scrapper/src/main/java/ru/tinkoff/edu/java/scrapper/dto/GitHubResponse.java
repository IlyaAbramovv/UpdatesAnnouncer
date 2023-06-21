package ru.tinkoff.edu.java.scrapper.dto;

import java.time.Instant;

public record GitHubResponse(
        String userName,
        String repoName,
        Instant dateTime
) {
}
