package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdatesAnnouncerBotTest {

    private final UpdatesAnnouncerBot bot = new UpdatesAnnouncerBot(" ", new ScrapperClient());

    @Test
    public void invalidCommandGiven() {
        String invalidCommand = "invalidCommand";
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.id()).thenReturn(1L);

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.chat()).thenReturn(chat);

        assertEquals("Unknown command: " + invalidCommand,
                bot.getSendMessage(null, message, invalidCommand).getParameters().get("text")
        );
    }

}