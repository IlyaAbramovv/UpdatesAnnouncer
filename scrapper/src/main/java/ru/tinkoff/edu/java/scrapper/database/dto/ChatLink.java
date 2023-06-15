package ru.tinkoff.edu.java.scrapper.database.dto;

public record ChatLink(
        Long id,
        Chat chat,
        Link link
) {
}
