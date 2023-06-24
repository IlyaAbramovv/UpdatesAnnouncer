package ru.tinkoff.edu.java.scrapper.service.jooq;

import ru.tinkoff.edu.java.scrapper.database.ChatRepository;
import ru.tinkoff.edu.java.scrapper.service.AbstractChatService;

public class JooqChatService extends AbstractChatService {
    public JooqChatService(ChatRepository chatRepository) {
        super(chatRepository);
    }
}
