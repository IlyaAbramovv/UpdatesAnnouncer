package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.database.jdbc.JdbcChatLinkRepository;
import ru.tinkoff.edu.java.scrapper.database.jdbc.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.database.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public LinkService linkService(JdbcLinkRepository linkRepository, JdbcChatRepository chatRepository, JdbcChatLinkRepository chatLinkRepository) {
        return new JdbcLinkService(linkRepository, chatRepository, chatLinkRepository);
    }

    @Bean
    public TgChatService tgChatService(JdbcChatRepository chatRepository) {
        return new JdbcChatService(chatRepository);
    }
}
