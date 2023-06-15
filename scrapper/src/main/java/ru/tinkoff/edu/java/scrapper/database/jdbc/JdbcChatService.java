package ru.tinkoff.edu.java.scrapper.database.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.database.dto.Chat;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

import java.util.List;

@Repository
public class JdbcChatService implements ChatService {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcChatService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Chat chat) {
        jdbcTemplate.update("INSERT INTO chat (chat_id) VALUES (?)", chat.id());
    }

    @Override
    public void remove(Chat chat) {
        jdbcTemplate.update("DELETE FROM chat WHERE chat_id =?", chat.id());
    }

    @Override
    public List<Chat> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM chat",
                (resultSet, rowNum) -> new Chat(resultSet.getLong("chat_id")));
    }
}
