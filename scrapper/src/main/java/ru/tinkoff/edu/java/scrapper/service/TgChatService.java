package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.database.entity.Chat;

import java.util.Collection;

public interface TgChatService {
    Chat register(Chat chat);

    Chat unregister(Chat chat);

    Collection<Chat> findAll();
}
