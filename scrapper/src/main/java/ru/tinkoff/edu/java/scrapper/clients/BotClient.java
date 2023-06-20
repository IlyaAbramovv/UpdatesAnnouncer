package ru.tinkoff.edu.java.scrapper.clients;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.database.dto.LinkUpdate;

public class BotClient {
    final WebClient webClient;

    public BotClient() {
        webClient = WebClient.create("http://localhost:8081");
    }

    public BotClient(String url) {
        this.webClient = WebClient.create(url);
    }

    public void update(LinkUpdate linkUpdate) {
        webClient.post().uri("/updates").bodyValue(linkUpdate).retrieve().toBodilessEntity().block();
    }
}
