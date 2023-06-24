package ru.tinkoff.edu.java.scrapper.controllers;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.database.entity.Link;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.time.Instant;

@RestController
@RequestMapping("/links")
public class LinksController {
    private final LinkService linkService;

    public LinksController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public ListLinksResponse getLinks(@RequestParam("Tg-Chat-Id") long tgChatId) {
        return linkService.listAll(tgChatId);
    }

    @PostMapping
    public LinkResponse addLinks(@RequestParam("Tg-Chat-Id") long tgChatId, @RequestBody AddLinkRequest addLinkRequest) {
        Link link = new Link(tgChatId, addLinkRequest.link().toString(), Instant.now());
        linkService.add(tgChatId, link);
        return new LinkResponse(tgChatId, addLinkRequest.link());
    }

    @DeleteMapping
    public LinkResponse delete(@RequestParam("Tg-Chat-Id") long tgChatId, @RequestBody RemoveLinkRequest removeLinkRequest) {
        return linkService.remove(tgChatId, removeLinkRequest);
    }
}
