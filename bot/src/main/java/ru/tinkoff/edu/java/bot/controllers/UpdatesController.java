package ru.tinkoff.edu.java.bot.controllers;

import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;
import ru.tinkoff.edu.java.bot.service.UpdatesAnnouncerBot;

@RestController
@RequestMapping("/updates")
public class UpdatesController {
    private final UpdatesAnnouncerBot bot;

    public UpdatesController(UpdatesAnnouncerBot bot) {
        this.bot = bot;
    }

    @PostMapping
    public void update(@RequestBody LinkUpdate linkUpdate) {
        System.out.println("received update");
        for (var chat : linkUpdate.tgChatIds()) {
            bot.sendMessage(new SendMessage(
                    chat,
                    "Update at link: " + linkUpdate.url()
            ));
        }

    }
}
