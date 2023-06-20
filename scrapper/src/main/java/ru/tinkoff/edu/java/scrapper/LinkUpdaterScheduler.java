package ru.tinkoff.edu.java.scrapper;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.link_parser.GitHubLinkParser;
import ru.tinkoff.edu.java.link_parser.LinkParser;
import ru.tinkoff.edu.java.link_parser.StackOverFlowLinkParser;
import ru.tinkoff.edu.java.scrapper.clients.BotClient;
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.StackOverFlowClient;
import ru.tinkoff.edu.java.scrapper.database.dto.Link;
import ru.tinkoff.edu.java.scrapper.database.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.database.service.ChatLinkService;
import ru.tinkoff.edu.java.scrapper.database.service.LinkService;

import java.net.URI;
import java.util.List;

@Component
public class LinkUpdaterScheduler {
    private final BotClient botClient;
    private final LinkService linkService;
    private final ChatLinkService chatLinkService;
    private final GitHubClient gitHubClient;
    private final StackOverFlowClient stackOverFlowClient;

    public LinkUpdaterScheduler(BotClient botClient, LinkService linkService, ChatLinkService chatLinkService, GitHubClient gitHubClient, StackOverFlowClient stackOverFlowClient) {
        this.botClient = botClient;
        this.linkService = linkService;
        this.chatLinkService = chatLinkService;
        this.gitHubClient = gitHubClient;
        this.stackOverFlowClient = stackOverFlowClient;
    }

    private void findUpdatedLinks() {

        List<Link> links = linkService.listAll();
        LinkParser gitHubLinkParser = new GitHubLinkParser();
        LinkParser sofLinkParser = new StackOverFlowLinkParser();
        for (var link : links) {
            var ghRes = gitHubLinkParser.parse(link.url());
            var sofRes = sofLinkParser.parse(link.url());
            if (ghRes != null) {
                var response = gitHubClient.fetchRepository(ghRes.result.get(0), ghRes.result.get(1));
                if (response.dateTime().isAfter(link.lastUpdate())) {
                    List<Long> ids = chatLinkService.findAll().stream().filter(cl -> cl.link().id().equals(link.id())).map(cl -> cl.chat().id()).toList();
                    LinkUpdate linkUpdate = new LinkUpdate(link.id(), URI.create(link.url()), "", ids, response.dateTime());
                    linkService.update(linkUpdate);
                    botClient.update(linkUpdate);
                }

            } else if (sofRes != null) {
                var response = stackOverFlowClient.fetchQuestion(sofRes.result.get(0));
                if (response.dateTime().isAfter(link.lastUpdate())) {
                    List<Long> ids = chatLinkService.findAll().stream().filter(cl -> cl.link().id().equals(link.id())).map(cl -> cl.chat().id()).toList();
                    LinkUpdate linkUpdate = new LinkUpdate(link.id(), URI.create(link.url()), "", ids, response.dateTime());
                    linkService.update(linkUpdate);
                    botClient.update(linkUpdate);
                }

            }
        }
    }

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")

    public void update() {
        System.out.println("Updating links");
        findUpdatedLinks();
    }

}
