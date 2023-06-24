package ru.tinkoff.edu.java.scrapper.database.jooq;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.database.dto.Link;
import ru.tinkoff.edu.java.scrapper.database.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.database.service.LinkService;

import java.sql.Timestamp;
import java.util.List;

import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.LINK;

@Repository
public class JooqLinkService implements LinkService {
    private final DSLContext dslContext;

    public JooqLinkService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Link add(Link link) {
        return dslContext
                .insertInto(LINK, LINK.URL, LINK.UPDATED_AT)
                .values(link.url(), Timestamp.from(link.lastUpdate()).toLocalDateTime())
                .returning(LINK.LINK_ID, LINK.URL, LINK.UPDATED_AT).fetchInto(Link.class)
                .stream().findAny().get();
    }

    @Override
    public Link remove(Link link) {
        dslContext
                .deleteFrom(LINK)
                .where(LINK.LINK_ID.eq((int) link.id()))
                .execute();
        return link;
    }

    @Override
    public List<Link> listAll() {
        return dslContext
                .selectFrom(LINK)
                .fetchInto(Link.class);
    }

    @Override
    public void update(LinkUpdate linkUpdate) {
        dslContext
                .update(LINK)
                .set(LINK.UPDATED_AT, Timestamp.from(linkUpdate.lastUpdate()).toLocalDateTime())
                .execute();
    }
}
