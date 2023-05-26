package ru.tinkoff.edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class UnknownCmd implements Command {
    private final long chatId;
    private final String command;

    public UnknownCmd(long chatId, String command) {
        this.chatId = chatId;
        this.command = command;
    }

    @Override
    public String command() {
        return command;
    }

    @Override
    public String description() {
        return "unkown";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(chatId, "Unknown command: " + command);
    }
}
