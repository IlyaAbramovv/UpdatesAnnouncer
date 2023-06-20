package ru.tinkoff.edu.java.scrapper.database.dto;

import java.time.OffsetDateTime;

public record Link(
        Long id,
        String url,
        OffsetDateTime lastUpdate
) {
}
