package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.database.jooq.JooqChatLinkRepository;
import ru.tinkoff.edu.java.scrapper.database.jooq.JooqChatRepository;
import ru.tinkoff.edu.java.scrapper.database.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqChatService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    public LinkService linkService(JooqLinkRepository linkRepository, JooqChatRepository chatRepository, JooqChatLinkRepository chatLinkRepository) {
        return new JooqLinkService(linkRepository, chatRepository, chatLinkRepository);
    }

    @Bean
    public TgChatService chatService(JooqChatRepository chatRepository) {
        return new JooqChatService(chatRepository);
    }
}
