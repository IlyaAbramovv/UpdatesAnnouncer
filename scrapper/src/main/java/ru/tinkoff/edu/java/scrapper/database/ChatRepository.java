package ru.tinkoff.edu.java.scrapper.database;

import ru.tinkoff.edu.java.scrapper.database.entity.Chat;

import java.util.Collection;

public interface ChatRepository {
    Chat register(Chat chat);

    Chat unregister(Chat chat);

    Collection<Chat> findAll();
}
