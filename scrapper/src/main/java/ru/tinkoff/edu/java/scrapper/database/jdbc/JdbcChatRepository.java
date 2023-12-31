package ru.tinkoff.edu.java.scrapper.database.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.database.ChatRepository;
import ru.tinkoff.edu.java.scrapper.database.entity.Chat;

import java.sql.Types;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public class JdbcChatRepository implements ChatRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Chat register(Chat chat) {
        jdbcTemplate.update("INSERT INTO chat (chat_id) VALUES (?)", chat.id());
        return chat;
    }

    @Transactional
    @Override
    public Chat unregister(Chat chat) {
        List<Long> ids = jdbcTemplate.query("select link_id from chat_link where chat_id = ?", new Object[]{chat.id()}, new int[]{Types.INTEGER},
                (rs, rn) -> rs.getLong("link_id"));

        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        if (ids.size() > 0) {
            String linksQ = String.format("delete from link where link_id in (%s)", inSql);
            jdbcTemplate.update(linksQ, ids.toArray());
        }
//        jdbcTemplate.update("DELETE FROM chat_link WHERE chat_id =?", chat.id());
        jdbcTemplate.update("DELETE FROM chat WHERE chat_id =?", chat.id());
        return chat;
    }

    @Override
    public Collection<Chat> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM chat",
                (resultSet, rowNum) -> new Chat(resultSet.getLong("chat_id")));
    }
}
