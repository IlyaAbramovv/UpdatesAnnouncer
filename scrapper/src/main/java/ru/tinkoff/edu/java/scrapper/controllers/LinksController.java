package ru.tinkoff.edu.java.scrapper.controllers;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

@RestController
@RequestMapping("/links")
public class LinksController {
    @GetMapping
    public ListLinksResponse getLinks(@RequestParam("Tg-Chat-Id") long tgChatId) {
        return null;
    }

    @PostMapping
    public LinkResponse addLinks(@RequestParam("Tg-Chat-Id") long tgChatId, @RequestBody AddLinkRequest addLinkRequest) {
        return null;
    }

    @DeleteMapping
    public LinkResponse delete(@RequestParam("Tg-Chat-Id") long tgChatId, @RequestBody RemoveLinkRequest removeLinkRequest) {
        return null;
    }
}
