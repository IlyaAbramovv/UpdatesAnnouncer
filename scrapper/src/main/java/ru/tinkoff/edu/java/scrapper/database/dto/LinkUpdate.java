package ru.tinkoff.edu.java.scrapper.database.dto;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public record LinkUpdate(
        long id,
        URI url,
        String description,
        List<Long> tgChatIds,
        OffsetDateTime lastUpdate
) {
}

