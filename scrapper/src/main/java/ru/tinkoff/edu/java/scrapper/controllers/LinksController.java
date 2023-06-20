package ru.tinkoff.edu.java.scrapper.controllers;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.database.dto.Chat;
import ru.tinkoff.edu.java.scrapper.database.dto.ChatLink;
import ru.tinkoff.edu.java.scrapper.database.dto.Link;
import ru.tinkoff.edu.java.scrapper.database.service.ChatLinkService;
import ru.tinkoff.edu.java.scrapper.database.service.LinkService;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/links")
public class LinksController {
    private final LinkService linkService;
    private final ChatLinkService chatLinkService;

    public LinksController(LinkService linkService, ChatLinkService chatLinkService) {
        this.linkService = linkService;
        this.chatLinkService = chatLinkService;
    }

    @GetMapping
    public ListLinksResponse getLinks(@RequestParam("Tg-Chat-Id") long tgChatId) {
        return chatLinkService.findAllByChatId(tgChatId);
    }

    @PostMapping
    public LinkResponse addLinks(@RequestParam("Tg-Chat-Id") long tgChatId, @RequestBody AddLinkRequest addLinkRequest) {
        Link link = new Link(tgChatId, addLinkRequest.link().toString(), OffsetDateTime.of(LocalDateTime.of(2000, 1, 1, 1, 1), ZoneOffset.ofHours(0)));
        Link added = linkService.add(link);
        chatLinkService.add(new ChatLink(
                tgChatId,
                new Chat(tgChatId),
                added
        ));
        return new LinkResponse(tgChatId, addLinkRequest.link());
    }

    @DeleteMapping
    public LinkResponse delete(@RequestParam("Tg-Chat-Id") long tgChatId, @RequestBody RemoveLinkRequest removeLinkRequest) {
        Link link = new Link(tgChatId, removeLinkRequest.link().toString(), OffsetDateTime.now());
        chatLinkService.remove(new ChatLink(tgChatId, new Chat(tgChatId), link));
        linkService.remove(link);
        return new LinkResponse(tgChatId, removeLinkRequest.link());
    }
}
