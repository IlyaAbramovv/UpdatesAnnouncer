package ru.tinkoff.edu.java.scrapper.clients;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;


public class ScrapperClient {
    private final WebClient webClient;

    public ScrapperClient() {
        webClient = WebClient.create("http://localhost:8080");
    }

    public ScrapperClient(String url) {
        webClient = WebClient.create(url);
    }

    public void registerChat(long id) {
        webClient.post().uri("/tg-chat/{id}", id).retrieve().toBodilessEntity().block();
    }

    public void deleteChat(long id) {
        webClient.delete().uri("/tg-chat/{id}", id).retrieve().toBodilessEntity().block();
    }

    public ListLinksResponse getLinks(long id) {
        return webClient.get().uri("/links?Tg-Chat-Id={id}", id).retrieve().bodyToMono(ListLinksResponse.class).block();
    }

    public LinkResponse addLink(long id, AddLinkRequest request) {
        return webClient.post().uri("/links?Tg-Chat-Id={id}", id).bodyValue(request).retrieve().bodyToMono(LinkResponse.class).block();
    }

    public LinkResponse deleteLink(long id, RemoveLinkRequest request) {
        return webClient.method(HttpMethod.DELETE).uri("/links?Tg-Chat-Id={id}", id).bodyValue(request).retrieve().bodyToMono(LinkResponse.class).block();
    }

}
