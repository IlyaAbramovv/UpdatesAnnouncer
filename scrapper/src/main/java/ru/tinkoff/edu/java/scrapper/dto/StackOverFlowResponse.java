package ru.tinkoff.edu.java.scrapper.dto;

import java.time.OffsetDateTime;

public record StackOverFlowResponse(
        String id,
        OffsetDateTime dateTime
) {
}
