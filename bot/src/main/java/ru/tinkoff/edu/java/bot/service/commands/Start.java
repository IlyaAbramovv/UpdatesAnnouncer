package ru.tinkoff.edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.scrapper.clients.ScrapperClient;

public class Start implements Command {
    final long chatId;
    final ScrapperClient scrapperClient;

    public Start(long chatId, ScrapperClient scrapperClient) {
        this.chatId = chatId;
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Connects bot to provided user";
    }

    @Override
    public SendMessage handle(Update update) {
        Keyboard keyboard = new ReplyKeyboardMarkup(
                new KeyboardButton("/help"),
                new KeyboardButton("/list"),
                new KeyboardButton("/track"),
                new KeyboardButton("/untrack"));


        scrapperClient.registerChat(chatId);
        return new SendMessage(chatId, "Starting...").replyMarkup(keyboard);
    }
}
