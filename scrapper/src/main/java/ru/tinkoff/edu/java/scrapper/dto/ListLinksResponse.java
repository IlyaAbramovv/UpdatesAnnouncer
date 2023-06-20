package ru.tinkoff.edu.java.scrapper.dto;

import java.net.URI;
import java.util.List;

public record ListLinksResponse(
        List<LinkResponse> links,
        int size
) {
    public boolean contains(URI link) {
        return links.stream().map(LinkResponse::url).toList().contains(link);
    }

    @Override
    public String toString() {
        return String.join("\n", links.stream().map(lr -> lr.url().toString()).toList());
    }
}
