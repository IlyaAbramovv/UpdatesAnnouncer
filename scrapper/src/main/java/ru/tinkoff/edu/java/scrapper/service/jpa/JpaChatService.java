package ru.tinkoff.edu.java.scrapper.service.jpa;

import ru.tinkoff.edu.java.scrapper.database.entity.Chat;
import ru.tinkoff.edu.java.scrapper.database.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import java.util.Collection;

public class JpaChatService implements TgChatService {
    private final JpaChatRepository jpaChatRepository;

    public JpaChatService(JpaChatRepository jpaChatRepository) {
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    public Chat register(Chat chat) {
        jpaChatRepository.save(chat);
        return chat;
    }

    @Override
    public Chat unregister(Chat chat) {
        jpaChatRepository.delete(chat);
        return chat;
    }

    @Override
    public Collection<Chat> findAll() {
        return jpaChatRepository.findAll();
    }
}
