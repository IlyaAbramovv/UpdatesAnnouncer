package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.database.ChatRepository;
import ru.tinkoff.edu.java.scrapper.database.entity.Chat;

import java.util.Collection;

public class AbstractChatService implements TgChatService {
    private final ChatRepository chatRepository;

    public AbstractChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Chat register(Chat chat) {
        return chatRepository.register(chat);
    }

    @Override
    public Chat unregister(Chat chat) {
        return chatRepository.unregister(chat);
    }

    @Override
    public Collection<Chat> findAll() {
        return chatRepository.findAll();
    }
}
