package ru.tinkoff.edu.java.scrapper.controllers;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/tg-chat/{id}")
@RestController
public class TgChatController {

    @PostMapping
    public void registerChat(@PathVariable("id") long id) {
        System.out.println("registered");
    }

    @DeleteMapping
    public void delete(@PathVariable("id") long id) {
    }


}
