package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.database.dto.Chat;

import java.util.List;

public interface ChatService {
    void add(Chat chat);

    void remove(Chat chat);

    List<Chat> findAll();
}
