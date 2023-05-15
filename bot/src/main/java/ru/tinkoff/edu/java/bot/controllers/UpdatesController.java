package ru.tinkoff.edu.java.bot.controllers;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;

@RestController
@RequestMapping("/updates")
public class UpdatesController {
    @PostMapping
    public void update(@RequestBody LinkUpdate linkUpdate) {
    }
}
