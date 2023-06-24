package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.database.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.database.entity.Link;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

import java.util.List;

public interface LinkService {
    Link add(long tgChatId, Link link);

    LinkResponse remove(long tgChatId, RemoveLinkRequest link);

    List<Link> listAll();

    ListLinksResponse listAll(long tgChatId);

    void update(LinkUpdate linkUpdate);

    List<Long> getUpdates(Link link);
}
