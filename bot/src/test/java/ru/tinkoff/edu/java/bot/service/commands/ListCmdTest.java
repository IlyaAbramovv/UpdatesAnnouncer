package ru.tinkoff.edu.java.bot.service.commands;

import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCmdTest {

    @Test
    public void emptyList() {
        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        Mockito.when(scrapperClient.getLinks(Mockito.any(Long.class))).thenReturn(new ListLinksResponse(List.of(), 0));

        SendMessage sendMessage = new ListCmd(1, scrapperClient).handle(null);

        assertEquals("No links are being tracked",
                sendMessage.getParameters().get("text"));
    }

}