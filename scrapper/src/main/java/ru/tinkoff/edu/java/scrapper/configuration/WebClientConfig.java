package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.clients.BotClient;
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.StackOverFlowClient;

@Configuration
public class WebClientConfig {
    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClient();
    }

    @Bean
    public StackOverFlowClient stackOverFlowClient() {
        return new StackOverFlowClient();
    }

    @Bean
    public BotClient botClient() {
        return new BotClient();
    }
}
