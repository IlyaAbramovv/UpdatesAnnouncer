import org.junit.After;
import org.junit.Test;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.tinkoff.edu.java.scrapper.database.dto.Chat;
import ru.tinkoff.edu.java.scrapper.database.dto.ChatLink;
import ru.tinkoff.edu.java.scrapper.database.dto.Link;
import ru.tinkoff.edu.java.scrapper.database.jdbc.JdbcChatLinkService;
import ru.tinkoff.edu.java.scrapper.database.jdbc.JdbcChatService;
import ru.tinkoff.edu.java.scrapper.database.jdbc.JdbcLinkService;

import java.time.Instant;

import static org.junit.Assert.assertTrue;

public class TestChatLink extends IntegrationEnvironment {
    JdbcChatLinkService chatLinkTable = new JdbcChatLinkService(jdbcTemplate);
    JdbcLinkService linkTable = new JdbcLinkService(jdbcTemplate);
    JdbcChatService jdbcChatService = new JdbcChatService(jdbcTemplate);

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "chat_link", "link", "chat");
    }

    @Test
    public void addChatLink() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "l1", Instant.now());
        jdbcChatService.register(chat);
        linkTable.add(link);

        link = linkTable.listAll().stream().toList().get(0);

        var chatLink = new ChatLink(1L, chat, link);
        chatLinkTable.add(chatLink);
        var res = chatLinkTable.findAll();
        Link finalLink = link;
        assertTrue(res.stream().anyMatch(
                cl -> cl.chat().equals(chat) && cl.link().url().equals(finalLink.url())
        ));
    }

    @Test
    public void delete() {
        Chat chat1 = new Chat(1L);
        Link link1 = new Link(1L, "l1", Instant.now());
        var chatLink1 = new ChatLink(1L, chat1, link1);
        Chat chat2 = new Chat(2L);
        Link link2 = new Link(2L, "l2", Instant.now());
        var chatLink2 = new ChatLink(2L, chat2, link2);
        linkTable.add(link1);
        linkTable.add(link2);
        jdbcChatService.register(chat1);
        jdbcChatService.register(chat2);
        chatLinkTable.add(chatLink1);
        chatLinkTable.add(chatLink2);

        chatLinkTable.remove(chatLink1);
        var res = chatLinkTable.findAll();

        assertTrue(res.stream().anyMatch(cl -> cl.link().url().equals(link2.url()) && cl.chat().equals(chat2)) &&
                res.stream().noneMatch(cl -> cl.link().url().equals(link1.url()) && cl.chat().equals(chat1)));
    }

    @Test
    public void findAll() {
        Chat chat1 = new Chat(1L);
        Link link1 = new Link(1L, "l1", Instant.now());
        Chat chat2 = new Chat(2L);
        Link link2 = new Link(2L, "l2", Instant.now());

        linkTable.add(link1);
        linkTable.add(link2);
        jdbcChatService.register(chat1);
        jdbcChatService.register(chat2);

        link1 = linkTable.listAll().stream().toList().get(0);
        link2 = linkTable.listAll().stream().toList().get(1);
        var chatLink1 = new ChatLink(1L, chat1, link1);
        var chatLink2 = new ChatLink(2L, chat2, link2);
        chatLinkTable.add(chatLink1);
        chatLinkTable.add(chatLink2);

        var res = chatLinkTable.findAll();

        System.out.println(res);
        Link finalLink = link1;
        Link finalLink1 = link2;
        assertTrue(res.stream().anyMatch(cl -> cl.chat().equals(chat1) && cl.link().equals(finalLink)) &&
                res.stream().anyMatch(cl -> cl.chat().equals(chat2) && cl.link().equals(finalLink1)));
    }
}
