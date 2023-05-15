package ru.tinkoff.edu.java.scrapper;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LinkUpdaterScheduler {
    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        System.out.println("Updating links");
    }

}
