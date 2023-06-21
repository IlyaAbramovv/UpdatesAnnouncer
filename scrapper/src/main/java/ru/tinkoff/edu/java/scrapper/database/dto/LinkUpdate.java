package ru.tinkoff.edu.java.scrapper.database.dto;

import java.net.URI;
import java.time.Instant;
import java.util.List;

public record LinkUpdate(
        long id,
        URI url,
        String description,
        List<Long> tgChatIds,
        Instant lastUpdate
) {
}

