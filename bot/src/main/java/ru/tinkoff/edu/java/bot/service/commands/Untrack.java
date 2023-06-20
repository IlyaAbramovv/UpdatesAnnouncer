package ru.tinkoff.edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.scrapper.clients.ScrapperClient;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

import java.net.URI;

public class Untrack implements Command {
    private final long chatId;
    private final ScrapperClient scrapperClient;

    public Untrack(long chatId, ScrapperClient scrapperClient) {

        this.chatId = chatId;
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String command() {
        return "untrack";
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public SendMessage handle(Update update) {
        String[] split = update.message().text().split(" ");
        if (split.length > 1) {
            String link = split[1];
            var tracked = scrapperClient.getLinks(chatId);
            if (!tracked.contains(URI.create(link))) {
                return new SendMessage(chatId, "Provided link is not tracked already");
            }
            scrapperClient.deleteLink(chatId, new RemoveLinkRequest(URI.create(link)));
            return new SendMessage(chatId, "Untracking link: " + link);
        }
        return new SendMessage(chatId, "No links provided");
    }
}
