package ru.tinkoff.edu.java.scrapper.clients;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.StackOverFlowResponse;

import java.time.Instant;

public class StackOverFlowClient {
    final WebClient webClient;

    public StackOverFlowClient() {
        webClient = WebClient.create("https://api.stackexchange.com");
    }

    public StackOverFlowClient(String url) {
        webClient = WebClient.create(url);
    }

    public StackOverFlowResponse fetchQuestion(String id) {
        try {
            return new StackOverFlowResponse(
                    id,
                    Instant.parse(new JSONObject(getQuestionInfo(id)).getString("last_activity_date")));
        } catch (JSONException e) {
            return null;
        }
    }

    private String getQuestionInfo(String id) {
        return webClient.get().uri("/posts/{id}", id).retrieve().bodyToMono(String.class).block();
    }
}
