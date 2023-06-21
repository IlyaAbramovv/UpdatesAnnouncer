package ru.tinkoff.edu.java.scrapper.dto;

import java.time.Instant;

public record StackOverFlowResponse(
        String id,
        Instant dateTime
) {
}
