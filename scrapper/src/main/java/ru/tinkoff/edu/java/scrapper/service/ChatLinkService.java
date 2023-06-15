package ru.tinkoff.edu.java.scrapper.service;

import java.util.List;

public interface ChatLinkService {
    void add(ru.tinkoff.edu.java.scrapper.database.dto.ChatLink chatLink);

    void remove(ru.tinkoff.edu.java.scrapper.database.dto.ChatLink chatLink);

    List<ru.tinkoff.edu.java.scrapper.database.dto.ChatLink> findAll();
}
