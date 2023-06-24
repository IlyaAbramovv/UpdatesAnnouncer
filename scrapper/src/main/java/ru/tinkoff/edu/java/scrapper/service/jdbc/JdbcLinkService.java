package ru.tinkoff.edu.java.scrapper.service.jdbc;

import ru.tinkoff.edu.java.scrapper.database.ChatLinkRepository;
import ru.tinkoff.edu.java.scrapper.database.ChatRepository;
import ru.tinkoff.edu.java.scrapper.database.LinkRepository;
import ru.tinkoff.edu.java.scrapper.service.AbstractLinkService;

public class JdbcLinkService extends AbstractLinkService {
    public JdbcLinkService(LinkRepository linkRepository, ChatRepository chatRepository, ChatLinkRepository chatLinkRepository) {
        super(linkRepository, chatRepository, chatLinkRepository);
    }
}
