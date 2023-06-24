package ru.tinkoff.edu.java.scrapper.database;

import ru.tinkoff.edu.java.scrapper.database.dto.ChatLink;
import ru.tinkoff.edu.java.scrapper.database.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

import java.util.List;

public interface ChatLinkRepository {
    void add(ChatLink chatLink);

    void remove(ChatLink chatLink);

    List<ChatLink> findAll();

    ListLinksResponse findAllByLinkId(long id);

    ListLinksResponse findAllByChatId(long id);

    LinkResponse findLinkByChatIdAndUrl(Chat chat, String url);

}
