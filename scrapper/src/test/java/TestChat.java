import org.junit.After;
import org.junit.Test;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.tinkoff.edu.java.scrapper.database.dto.Chat;
import ru.tinkoff.edu.java.scrapper.database.jdbc.JdbcChatService;

import static org.junit.Assert.assertTrue;

public class TestChat extends IntegrationEnvironment {
    JdbcChatService jdbcChatService = new JdbcChatService(jdbcTemplate);

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "chat");
    }

    @Test
    public void addChat() {
        Chat chat = new Chat(2L);

        jdbcChatService.register(chat);
        var res = jdbcChatService.findAll();

        assertTrue(res.stream().anyMatch(c -> c.equals(chat)));
    }

    @Test
    public void delete() {
        Chat chat1 = new Chat(2L);
        Chat chat2 = new Chat(3L);
        jdbcChatService.register(chat1);
        jdbcChatService.register(chat2);

        jdbcChatService.unregister(chat1);
        var res = jdbcChatService.findAll();

        System.out.println(res);

        assertTrue(res.contains(chat2) && !res.contains(chat1));
    }

    @Test
    public void findAll() {
        System.out.println(jdbcChatService.findAll());

        Chat chat1 = new Chat(2L);
        Chat chat2 = new Chat(3L);
        Chat chat3 = new Chat(4L);
        jdbcChatService.register(chat1);
        jdbcChatService.register(chat2);
        jdbcChatService.register(chat3);

        var res = jdbcChatService.findAll();

        assertTrue(res.contains(chat1) && res.contains(chat2) && res.contains(chat3));
    }
}
