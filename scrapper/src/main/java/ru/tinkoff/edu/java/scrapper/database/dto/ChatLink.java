package ru.tinkoff.edu.java.scrapper.database.dto;

import ru.tinkoff.edu.java.scrapper.database.entity.Chat;
import ru.tinkoff.edu.java.scrapper.database.entity.Link;

public record ChatLink(
        long id,
        Chat chat,
        Link link
) {
}
