package ru.tinkoff.edu.java.scrapper.database.jooq;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.database.ChatLinkRepository;
import ru.tinkoff.edu.java.scrapper.database.dto.ChatLink;
import ru.tinkoff.edu.java.scrapper.database.entity.Chat;
import ru.tinkoff.edu.java.scrapper.database.entity.Link;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

import java.time.Instant;
import java.util.List;

import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.CHAT_LINK;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.LINK;

@Repository
public class JooqChatLinkRepository implements ChatLinkRepository {
    private final DSLContext dslContext;

    public JooqChatLinkRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void add(ChatLink chatLink) {
        dslContext
                .insertInto(CHAT_LINK, CHAT_LINK.CHAT_ID, CHAT_LINK.LINK_ID)
                .values((int) chatLink.chat().id(), (int) chatLink.link().id())
                .execute();
    }

    @Override
    public void remove(ChatLink chatLink) {
        dslContext
                .deleteFrom(CHAT_LINK)
                .where(CHAT_LINK.CHAT_ID.eq((int) chatLink.chat().id()))
                .and(CHAT_LINK.LINK_ID.eq((int) chatLink.link().id()))
                .execute();
    }

    @Override
    public List<ChatLink> findAll() {
        record R(long clId, long chatId, long linkId, String url, Instant updated_at) {
        }
        return dslContext
                .select(CHAT_LINK.CHAT_LINK_ID, CHAT_LINK.CHAT_ID, LINK.LINK_ID, LINK.URL, LINK.UPDATED_AT)
                .from(CHAT_LINK).innerJoin(LINK)
                .on(CHAT_LINK.LINK_ID.eq(LINK.LINK_ID))
                .fetchInto(R.class)
                .stream()
                .map(r -> new ChatLink(
                        r.clId,
                        new Chat(r.chatId),
                        new Link(
                                r.linkId,
                                r.url,
                                r.updated_at
                        )
                )).toList();
    }

    @Override
    public ListLinksResponse findAllByLinkId(long id) {
        var links = dslContext
                .select(LINK.LINK_ID, LINK.URL)
                .from(LINK)
                .innerJoin(CHAT_LINK)
                .on(CHAT_LINK.LINK_ID.eq(LINK.LINK_ID))
                .and(CHAT_LINK.LINK_ID.eq((int) id))
                .fetchInto(LinkResponse.class);
        return new ListLinksResponse(links, links.size());
    }

    @Override
    public ListLinksResponse findAllByChatId(long id) {
        var links = dslContext
                .select(LINK.LINK_ID, LINK.URL)
                .from(LINK)
                .innerJoin(CHAT_LINK)
                .on(CHAT_LINK.LINK_ID.eq(LINK.LINK_ID))
                .and(CHAT_LINK.CHAT_ID.eq((int) id))
                .fetchInto(LinkResponse.class);
        return new ListLinksResponse(links, links.size());
    }

    @Override
    public LinkResponse findLinkByChatIdAndUrl(Chat chat, String url) {
        return dslContext
                .select(LINK.LINK_ID, LINK.URL)
                .from(LINK)
                .innerJoin(CHAT_LINK)
                .on(LINK.LINK_ID.eq(CHAT_LINK.LINK_ID))
                .where(CHAT_LINK.CHAT_ID.eq((int) chat.id()))
                .and(LINK.URL.eq(url))
                .fetchInto(LinkResponse.class).stream().findAny().get();
    }
}
