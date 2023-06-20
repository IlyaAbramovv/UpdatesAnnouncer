package ru.tinkoff.edu.java.scrapper.database.service;

import ru.tinkoff.edu.java.scrapper.database.dto.ChatLink;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

import java.util.List;

public interface ChatLinkService {
    void add(ChatLink chatLink);

    void remove(ChatLink chatLink);

    List<ChatLink> findAll();

    ListLinksResponse findAllByLinkId(long id);

    ListLinksResponse findAllByChatId(long id);
}
