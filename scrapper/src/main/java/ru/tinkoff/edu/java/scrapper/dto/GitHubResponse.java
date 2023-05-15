package ru.tinkoff.edu.java.scrapper.dto;

import java.time.OffsetDateTime;

public record GitHubResponse(
        String userName,
        String repoName,
        OffsetDateTime dateTime
) {
}
