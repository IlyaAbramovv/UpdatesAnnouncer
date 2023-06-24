package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.database.ChatLinkRepository;
import ru.tinkoff.edu.java.scrapper.database.ChatRepository;
import ru.tinkoff.edu.java.scrapper.database.LinkRepository;
import ru.tinkoff.edu.java.scrapper.database.dto.ChatLink;
import ru.tinkoff.edu.java.scrapper.database.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.database.entity.Chat;
import ru.tinkoff.edu.java.scrapper.database.entity.Link;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

import java.time.Instant;
import java.util.List;

public class AbstractLinkService implements LinkService {
    private final LinkRepository linkRepository;
    private final ChatRepository chatRepository;
    private final ChatLinkRepository chatLinkRepository;

    public AbstractLinkService(LinkRepository linkRepository, ChatRepository chatRepository, ChatLinkRepository chatLinkRepository) {
        this.linkRepository = linkRepository;
        this.chatRepository = chatRepository;
        this.chatLinkRepository = chatLinkRepository;
    }

    @Override
    public Link add(long tgChatId, Link link) {
        linkRepository.add(link);
        chatLinkRepository.add(new ChatLink(
                tgChatId,
                new Chat(tgChatId),
                link
        ));
        return link;
    }

    @Override
    public LinkResponse remove(long tgChatId, RemoveLinkRequest removeLinkRequest) {
        long id = chatLinkRepository.findLinkByChatIdAndUrl(new Chat(tgChatId), removeLinkRequest.link().toString()).id();
        Link link = new Link(id, removeLinkRequest.link().toString(), Instant.now());
        linkRepository.remove(link);
        return new LinkResponse(tgChatId, removeLinkRequest.link());
    }

    @Override
    public List<Link> listAll() {
        return linkRepository.listAll();
    }

    @Override
    public ListLinksResponse listAll(long tgChatId) {
        return chatLinkRepository.findAllByLinkId(tgChatId);
    }

    @Override
    public List<Long> getUpdates(Link link) {
        return chatLinkRepository.findAll().stream().filter(cl -> cl.link().id() == link.id()).map(cl -> cl.chat().id()).toList();
    }

    @Override
    public void update(LinkUpdate linkUpdate) {
        linkRepository.update(linkUpdate);
    }
}
