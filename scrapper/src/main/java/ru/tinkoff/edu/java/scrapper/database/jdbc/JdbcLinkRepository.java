package ru.tinkoff.edu.java.scrapper.database.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.database.LinkRepository;
import ru.tinkoff.edu.java.scrapper.database.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.database.entity.Link;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class JdbcLinkRepository implements LinkRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcLinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Link add(Link link) {
        return jdbcTemplate.query("INSERT INTO link (url, updated_at) VALUES (?, ?) RETURNING link_id",
                (rs, rn) -> new Link(rs.getLong("link_id"), link.url(), link.lastUpdate()), link.url(), Timestamp.from(link.lastUpdate())).stream().findAny().get();
    }

    @Transactional
    @Override
    public Link remove(Link link) {
        jdbcTemplate.update("DELETE FROM link WHERE link_id = ?", link.id());
        return link;
    }

    @Override
    public List<Link> listAll() {
        return jdbcTemplate.query(
                "SELECT * FROM link",
                (rs, rowNum) ->
                        new Link(
                                rs.getLong("link_id"),
                                rs.getString("url"),
                                rs.getTimestamp("updated_at").toInstant()));
    }

    @Override
    public void update(LinkUpdate linkUpdate) {
        jdbcTemplate.update("update link set updated_at = ? where link_id = ?", Timestamp.from(linkUpdate.lastUpdate()), linkUpdate.id());
    }
}
