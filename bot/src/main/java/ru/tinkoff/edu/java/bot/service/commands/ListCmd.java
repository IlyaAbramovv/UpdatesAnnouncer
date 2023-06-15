package ru.tinkoff.edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.scrapper.clients.ScrapperClient;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

public class ListCmd implements Command {
    private final long chatId;
    private final ScrapperClient scrapperClient;

    public ListCmd(long chatId, ScrapperClient scrapperClient) {

        this.chatId = chatId;
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public SendMessage handle(Update update) {
        ListLinksResponse links = scrapperClient.getLinks(chatId);
        if (links == null || links.size() == 0) {
            return new SendMessage(chatId, "No links are being tracked");
        } else return new SendMessage(chatId, links.toString());

    }
}
