package ru.tinkoff.edu.java.scrapper.database.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.database.dto.Link;

import java.util.List;

@Repository
public class JdbcLinkService implements ru.tinkoff.edu.java.scrapper.service.LinkService {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLinkService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Link link) {
        jdbcTemplate.update("INSERT INTO link (url) VALUES (?)", link.url());
    }

    @Override
    public void remove(Link link) {
        jdbcTemplate.update("DELETE FROM link WHERE url =?", link.url());
    }

    @Override
    public List<Link> findAll() {
        return jdbcTemplate.query("SELECT * FROM link", (rs, rowNum) -> new Link(rs.getLong("link_id"), rs.getString("url")));
    }

    public void removeAll() {
        jdbcTemplate.update("TRUNCATE TABLE link");
    }

}
