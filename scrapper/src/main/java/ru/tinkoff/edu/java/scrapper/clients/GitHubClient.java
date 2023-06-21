package ru.tinkoff.edu.java.scrapper.clients;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;

import java.time.Instant;

public class GitHubClient {
    private final WebClient webClient;
    public GitHubClient() {
        webClient = WebClient.create("https://api.github.com");
    }

    public GitHubClient(String url) {
        this.webClient = WebClient.create(url);
    }

    public GitHubResponse fetchRepository(String userName, String repoName) {
        try {
            return new GitHubResponse(
                    userName, repoName,
                    Instant.parse(new JSONObject(getRepo(userName, repoName)).getString("pushed_at"))
            );
        } catch (JSONException e) {
            return null;
        }

    }

    private String getRepo(String userName, String repoName) {
        return webClient.get().uri("/repos/{userName}/{repoName}", userName, repoName)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
