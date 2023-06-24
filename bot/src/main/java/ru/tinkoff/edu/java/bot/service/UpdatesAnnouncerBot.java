package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.service.commands.*;

import java.util.List;


@Component
public class UpdatesAnnouncerBot implements Bot {
    private final TelegramBot bot;
    private final ScrapperClient scrapperClient;

    public UpdatesAnnouncerBot(@Value("${app.token}") String token, ScrapperClient scrapperClient) {
        bot = new TelegramBot(token);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
    }

    @Override
    public int process(List<Update> updates) {
        for (var update : updates) {
            try {
                Message message = update.message();
                String messageText = message.text().split(" ")[0];

                SendMessage sendMessage = getSendMessage(update, message, messageText);
                SendResponse response = sendMessage(sendMessage);
                System.out.println(response.isOk());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }


        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public SendResponse sendMessage(SendMessage sendMessage) {
        return bot.execute(sendMessage);
    }

    SendMessage getSendMessage(Update update, Message message, String messageText) {
        return switch (messageText) {
            case "/start" -> new Start(message.chat().id(), scrapperClient).handle(update);
            case "/help" -> new Help(message.chat().id(), scrapperClient).handle(update);
            case "/track" -> new Track(message.chat().id(), scrapperClient).handle(update);
            case "/untrack" -> new Untrack(message.chat().id(), scrapperClient).handle(update);
            case "/list" -> new ListCmd(message.chat().id(), scrapperClient).handle(update);
            default -> new UnknownCmd(message.chat().id(), messageText).handle(update);
        };
    }


    @Override
    public void start() {
        bot.setUpdatesListener(this);
    }

    @Override
    public void close() {
        bot.shutdown();
    }
}
