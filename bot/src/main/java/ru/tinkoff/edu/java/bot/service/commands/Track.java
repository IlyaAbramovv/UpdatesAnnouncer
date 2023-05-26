package ru.tinkoff.edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.scrapper.clients.ScrapperClient;

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


        return new SendMessage(chatId, split.length > 1 ? "Tracking link: " + split[1] : "No links provided");
    }
}
