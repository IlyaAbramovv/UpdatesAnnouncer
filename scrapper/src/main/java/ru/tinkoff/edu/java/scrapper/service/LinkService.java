package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.database.dto.Link;

import java.util.List;

public interface LinkService {
    void add(Link link);

    void remove(Link link);

    List<Link> findAll();
}
