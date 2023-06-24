package ru.tinkoff.edu.java.scrapper.database.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.database.entity.Link;

import java.util.List;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {

    @Transactional
    @Query("select l from Link l join Chat c where c.id = ?1")
    List<Link> findAllByChatId(long tgChatId);

    Link findByUrl(String url);
}
