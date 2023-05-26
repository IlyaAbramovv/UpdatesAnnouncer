package ru.tinkoff.edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.scrapper.clients.ScrapperClient;

import java.util.Map;
import java.util.stream.Collectors;

public class Help implements Command {
    private final long chatId;
    private final ScrapperClient scrapperClient;

    private final Map<String, String> commands = Map.of(
            "/start", "Запустить бота",
            "/help", "Доступные команды",
            "/track", "Начать отслеживать ссылку",
            "/untrack", "Перестать отслеживать ссылку",
            "/list", "Получить отслеживаемые ссылки"
    );
    private final String text = commands.entrySet().stream().map(e -> e.getKey() + " " + e.getValue()).collect(Collectors.joining("\n"));

    public Help(long chatId, ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
        this.chatId = chatId;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(chatId, text);
    }
}
