package ru.tinkoff.edu.java.scrapper.database.service;

import ru.tinkoff.edu.java.scrapper.database.dto.Chat;

import java.util.Collection;

public interface TgChatService {
    Chat register(Chat chat);

    Chat unregister(Chat chat);

    Collection<Chat> findAll();
}
