package ru.tinkoff.edu.java.scrapper.service.jpa;

import ru.tinkoff.edu.java.scrapper.database.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.database.entity.Chat;
import ru.tinkoff.edu.java.scrapper.database.entity.Link;
import ru.tinkoff.edu.java.scrapper.database.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.database.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.HashSet;
import java.util.List;

public class JpaLinkService implements LinkService {
    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;

    public JpaLinkService(JpaLinkRepository jpaLinkRepository, JpaChatRepository jpaChatRepository) {
        this.jpaLinkRepository = jpaLinkRepository;
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    public Link add(long tgChatId, Link link) {
        Chat chat = jpaChatRepository.findById(tgChatId).orElseThrow();
        Link addLink = jpaLinkRepository.findByUrl(link.url());
        if (addLink == null) {
            addLink = new Link().setUrl(link.url()).setLastUpdate(link.lastUpdate()).setChats(new HashSet<>());
            jpaLinkRepository.save(addLink);
            addLink = jpaLinkRepository.findByUrl(link.url());
        }
        addLink.getChats().add(chat);
        chat.getLinks().add(addLink);
        jpaLinkRepository.save(addLink);
        jpaChatRepository.save(chat);
        return addLink;
    }

    @Override
    public LinkResponse remove(long tgChatId, RemoveLinkRequest link) {
        Chat chat = jpaChatRepository.findById(tgChatId).orElseThrow();
        Link removeLink = jpaLinkRepository.findByUrl(link.link().toString());
        chat.getLinks().remove(removeLink);
        removeLink.getChats().remove(chat);
        if (removeLink.getChats().isEmpty()) {
            jpaLinkRepository.delete(removeLink);
        }
        return new LinkResponse(removeLink.id(), URI.create(removeLink.url()));
    }

    @Override
    public List<Link> listAll() {
        return jpaLinkRepository.findAll();
    }

    @Override
    public ListLinksResponse listAll(long tgChatId) {
        List<Link> res = jpaLinkRepository.findAllByChatId(tgChatId);
        return new ListLinksResponse(res.stream().map(l -> new LinkResponse(l.id(), URI.create(l.url()))).toList(), res.size());
    }

    @Override
    public void update(LinkUpdate linkUpdate) {
        Link link = jpaLinkRepository.findByUrl(linkUpdate.url().toString());
        link.setLastUpdate(linkUpdate.lastUpdate());
        jpaLinkRepository.save(link);
    }

    @Override
    public List<Long> getUpdates(Link link) {
        return link.getChats().stream().map(Chat::id).toList();
    }
}
