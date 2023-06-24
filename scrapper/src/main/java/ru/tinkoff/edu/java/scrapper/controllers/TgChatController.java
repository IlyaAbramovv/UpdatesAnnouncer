package ru.tinkoff.edu.java.scrapper.controllers;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.database.entity.Chat;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RequestMapping("/tg-chat/{id}")
@RestController
public class TgChatController {
    private final TgChatService tgChatService;

    public TgChatController(TgChatService tgChatService) {
        this.tgChatService = tgChatService;
    }

    @PostMapping

    public void registerChat(@PathVariable("id") long id) {
        tgChatService.register(new Chat(id));
    }

    @DeleteMapping
    public void delete(@PathVariable("id") long id) {
        tgChatService.unregister(new Chat(id));
    }


}
