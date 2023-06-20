package ru.tinkoff.edu.java.scrapper.database.service;

import ru.tinkoff.edu.java.scrapper.database.dto.Link;
import ru.tinkoff.edu.java.scrapper.database.dto.LinkUpdate;

import java.util.List;

public interface LinkService {
    Link add(Link link);

    Link remove(Link link);

    List<Link> listAll();

    void update(LinkUpdate linkUpdate);
}
