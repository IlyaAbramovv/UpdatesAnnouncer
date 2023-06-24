package ru.tinkoff.edu.java.scrapper;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.link_parser.GitHubLinkParser;
import ru.tinkoff.edu.java.link_parser.LinkParser;
import ru.tinkoff.edu.java.link_parser.StackOverFlowLinkParser;
import ru.tinkoff.edu.java.scrapper.clients.BotClient;
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.StackOverFlowClient;
import ru.tinkoff.edu.java.scrapper.database.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.database.entity.Link;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.StackOverFlowResponse;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@Component
public class LinkUpdaterScheduler {
    private final BotClient botClient;
    private final LinkService linkService;
    private final GitHubClient gitHubClient;
    private final StackOverFlowClient stackOverFlowClient;

    public LinkUpdaterScheduler(BotClient botClient,
                                LinkService linkService,
                                GitHubClient gitHubClient,
                                StackOverFlowClient stackOverFlowClient) {
        this.botClient = botClient;
        this.linkService = linkService;
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
                GitHubResponse response = gitHubClient.fetchRepository(ghRes.result.get(0), ghRes.result.get(1));
                handleResponse(link, response.dateTime());

            } else if (sofRes != null) {
                StackOverFlowResponse response = stackOverFlowClient.fetchQuestion(sofRes.result.get(0));
                handleResponse(link, response.dateTime());

            }
        }
    }

    private void handleResponse(Link link, Instant instant) {
        if (instant.isAfter(link.lastUpdate())) {
            List<Long> ids = linkService.getUpdates(link);
            LinkUpdate linkUpdate = new LinkUpdate(link.id(), URI.create(link.url()), "", ids, instant);
            linkService.update(linkUpdate);
            botClient.update(linkUpdate);
        }
    }

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")

    public void update() {
        System.out.println("Updating links");
        findUpdatedLinks();
    }

}
