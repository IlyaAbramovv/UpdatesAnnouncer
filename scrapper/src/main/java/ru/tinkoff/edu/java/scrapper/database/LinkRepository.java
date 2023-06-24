package ru.tinkoff.edu.java.scrapper.database;

import ru.tinkoff.edu.java.scrapper.database.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.database.entity.Link;

import java.util.List;

public interface LinkRepository {
    Link add(Link link);

    Link remove(Link link);

    List<Link> listAll();

    void update(LinkUpdate linkUpdate);
}
