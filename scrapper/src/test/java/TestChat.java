import org.junit.After;
import org.junit.Test;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.tinkoff.edu.java.scrapper.database.entity.Chat;
import ru.tinkoff.edu.java.scrapper.database.jdbc.JdbcChatRepository;

import static org.junit.Assert.assertTrue;

public class TestChat extends IntegrationEnvironment {
    JdbcChatRepository jdbcChatRepository = new JdbcChatRepository(jdbcTemplate);

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "chat");
    }

    @Test
    public void addChat() {
        Chat chat = new Chat(2L);

        jdbcChatRepository.register(chat);
        var res = jdbcChatRepository.findAll();

        assertTrue(res.stream().anyMatch(c -> c.equals(chat)));
    }

    @Test
    public void delete() {
        Chat chat1 = new Chat(2L);
        Chat chat2 = new Chat(3L);
        jdbcChatRepository.register(chat1);
        jdbcChatRepository.register(chat2);

        jdbcChatRepository.unregister(chat1);
        var res = jdbcChatRepository.findAll();

        System.out.println(res);

        assertTrue(res.contains(chat2) && !res.contains(chat1));
    }

    @Test
    public void findAll() {
        System.out.println(jdbcChatRepository.findAll());

        Chat chat1 = new Chat(2L);
        Chat chat2 = new Chat(3L);
        Chat chat3 = new Chat(4L);
        jdbcChatRepository.register(chat1);
        jdbcChatRepository.register(chat2);
        jdbcChatRepository.register(chat3);

        var res = jdbcChatRepository.findAll();

        assertTrue(res.contains(chat1) && res.contains(chat2) && res.contains(chat3));
    }
}
