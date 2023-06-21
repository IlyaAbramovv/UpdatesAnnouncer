package ru.tinkoff.edu.java.scrapper.database.jooq;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.database.dto.Chat;
import ru.tinkoff.edu.java.scrapper.database.service.TgChatService;

import java.util.Collection;
import java.util.List;

import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.*;

@Repository
public class JooqChatService implements TgChatService {
    private final DSLContext dslContext;

    public JooqChatService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Chat register(Chat chat) {
        dslContext
                .insertInto(CHAT)
                .values(chat.id()).execute();
        return chat;
    }

    @Override
    public Chat unregister(Chat chat) {
        List<Long> ids = dslContext.select(LINK.LINK_ID).from(CHAT_LINK).where(CHAT_LINK.CHAT_ID.eq((int) chat.id())).fetchInto(Long.class);
        dslContext.deleteFrom(CHAT).where(CHAT.CHAT_ID.eq((int) chat.id())).execute();
        dslContext.deleteFrom(LINK).where(LINK.LINK_ID.in(ids)).execute();
        return chat;
    }

    @Override
    public Collection<Chat> findAll() {
        return dslContext.select(CHAT.CHAT_ID).from(CHAT).fetchInto(Chat.class);
    }
}
