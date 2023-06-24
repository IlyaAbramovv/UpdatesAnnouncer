package ru.tinkoff.edu.java.scrapper.database.dto;

import java.time.Instant;

public record Link(
        long id,
        String url,
        Instant lastUpdate
) {
}
