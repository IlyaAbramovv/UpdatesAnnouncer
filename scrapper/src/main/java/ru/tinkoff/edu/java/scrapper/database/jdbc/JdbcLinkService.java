package ru.tinkoff.edu.java.scrapper.database.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.database.dto.Link;
import ru.tinkoff.edu.java.scrapper.database.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.database.service.LinkService;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

@Repository
public class JdbcLinkService implements LinkService {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLinkService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Link add(Link link) {
        return jdbcTemplate.query("INSERT INTO link (url, updated_at) VALUES (?, ?) RETURNING link_id",
                (rs, rn) -> new Link(rs.getLong("link_id"), link.url(), link.lastUpdate()), link.url(), link.lastUpdate()).stream().findAny().get();
    }

    @Transactional
    @Override
    public Link remove(Link link) {
        List<Long> ids = jdbcTemplate.query("select * from link inner join chat_link on link.link_id = chat_link.link_id and chat_id = ? and link.url = ?", new Object[]{link.id(), link.url()}, new int[]{Types.INTEGER, Types.VARCHAR}, (rs, rn) -> rs.getLong("link_id"));

        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String q1 = String.format("DELETE FROM chat_link WHERE link_id in (%s)", inSql);
        String q2 = String.format("DELETE FROM link WHERE link_id in (%s)", inSql);
        jdbcTemplate.update(q1, ids.toArray());
        jdbcTemplate.update(q2, ids.toArray());
        return link;
    }

    @Override
    public List<Link> listAll() {
        return jdbcTemplate.query("SELECT * FROM link", (rs, rowNum) -> new Link(rs.getLong("link_id"), rs.getString("url"), OffsetDateTime.ofInstant(rs.getTimestamp("updated_at").toLocalDateTime().toInstant(ZoneOffset.UTC), TimeZone.getDefault().toZoneId())));
    }

    @Override
    public void update(LinkUpdate linkUpdate) {
        jdbcTemplate.update("update link set updated_at = ? where link_id = ?", Timestamp.valueOf(linkUpdate.lastUpdate().toLocalDateTime()), linkUpdate.id());
    }

    public void removeAll() {
        jdbcTemplate.update("TRUNCATE TABLE link");
    }

}
