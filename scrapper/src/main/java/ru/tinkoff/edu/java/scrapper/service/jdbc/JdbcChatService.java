package ru.tinkoff.edu.java.scrapper.service.jdbc;

import ru.tinkoff.edu.java.scrapper.database.ChatRepository;
import ru.tinkoff.edu.java.scrapper.service.AbstractChatService;

public class JdbcChatService extends AbstractChatService {
    public JdbcChatService(ChatRepository chatRepository) {
        super(chatRepository);
    }
}
