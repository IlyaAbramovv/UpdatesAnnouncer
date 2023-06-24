package ru.tinkoff.edu.java.scrapper.database.dto;

public record ChatLink(
        long id,
        Chat chat,
        Link link
) {
}
