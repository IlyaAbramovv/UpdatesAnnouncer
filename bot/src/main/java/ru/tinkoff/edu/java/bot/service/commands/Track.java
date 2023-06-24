package ru.tinkoff.edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.val;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.AddLinkRequest;

import java.net.URI;

public class Track implements Command {
    private final long chatId;
    private final ScrapperClient scrapperClient;

    public Track(long chatId, ScrapperClient scrapperClient) {
        this.chatId = chatId;
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String command() {
        return "/track";
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

            val tracked = scrapperClient.getLinks(chatId);
            if (tracked.contains(URI.create(link))) {
                return new SendMessage(chatId, "Provided link is already being tracked");
            }
            scrapperClient.addLink(chatId, new AddLinkRequest(URI.create(link)));
            return new SendMessage(chatId, "Tracking link: " + link);
        }
        return new SendMessage(chatId, "No links provided");
    }
}
