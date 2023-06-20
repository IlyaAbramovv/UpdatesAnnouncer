package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.database.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.database.service.LinkService;

import javax.sql.DataSource;

@Configuration
public class JdbcConfiguration {
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public LinkService linkService(JdbcTemplate jdbcTemplate) {
        return new JdbcLinkService(jdbcTemplate);
    }
}
