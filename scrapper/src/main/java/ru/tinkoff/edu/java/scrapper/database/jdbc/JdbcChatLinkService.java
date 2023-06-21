package ru.tinkoff.edu.java.scrapper.database.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.database.dto.Chat;
import ru.tinkoff.edu.java.scrapper.database.dto.ChatLink;
import ru.tinkoff.edu.java.scrapper.database.dto.Link;
import ru.tinkoff.edu.java.scrapper.database.service.ChatLinkService;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

import java.net.URI;
import java.sql.Types;
import java.util.List;

@Repository
public class JdbcChatLinkService implements ChatLinkService {
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcChatLinkService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(ChatLink chatLink) {
        jdbcTemplate.update("INSERT INTO chat_link (chat_id, link_id) VALUES (?,?)", chatLink.chat().id(), chatLink.link().id());
    }

    @Override
    public void remove(ChatLink chatLink) {
        jdbcTemplate.update("DELETE FROM chat_link WHERE chat_id =? AND link_id =?", chatLink.chat().id(), chatLink.link().id());
    }

    @Override
    public List<ChatLink> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat_link", (rs, rowNum) -> {
            Link link = jdbcTemplate.query("SELECT * FROM link WHERE link_id =?", new Object[]{rs.getLong("link_id")}, new int[]{Types.INTEGER},
                    (rs1, rn1) -> new Link(
                            rs1.getLong("link_id"),
                            rs1.getString("url"),
                            rs1.getTimestamp("updated_at").toInstant()
                    )).stream().findAny().orElse(null);
            return new ChatLink(
                    rs.getLong("chat_link_id"),
                    new Chat(rs.getLong("chat_id")),
                    link
            );
        });
    }

    @Override
    public ListLinksResponse findAllByLinkId(long id) {
        List<LinkResponse> list = jdbcTemplate.query("select * from chat_link inner join link on chat_link.link_id = link.link_id and link.link_id = ?",
                new Object[]{id}, new int[]{Types.INTEGER},
                (rs, rn) -> new LinkResponse(rs.getLong("link_id"), URI.create(rs.getString("url")))).stream().toList();
        return new ListLinksResponse(list, list.size());
    }

    @Override
    public ListLinksResponse findAllByChatId(long id) {
        List<LinkResponse> list = jdbcTemplate.query("select * from chat_link inner join link on chat_link.link_id = link.link_id and chat_link.chat_id = ?",
                new Object[]{id}, new int[]{Types.INTEGER},
                (rs, rn) -> new LinkResponse(rs.getLong("link_id"), URI.create(rs.getString("url")))).stream().toList();
        return new ListLinksResponse(list, list.size());
    }

    @Override
    public LinkResponse findLinkByChatIdAndUrl(Chat chat, String url) {
        return jdbcTemplate.query("select link.link_id from link inner join chat_link cl on link.link_id = cl.link_id where chat_id = ? and url = ?",
                new Object[]{chat.id(), url}, new int[]{Types.INTEGER, Types.VARCHAR},
                (rs, rn) -> new LinkResponse(rs.getLong("link_id"), URI.create(url))).stream().findAny().orElse(null);
    }
}
