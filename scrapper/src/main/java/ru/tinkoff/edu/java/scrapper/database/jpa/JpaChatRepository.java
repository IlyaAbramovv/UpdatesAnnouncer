package ru.tinkoff.edu.java.scrapper.database.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.database.entity.Chat;

public interface JpaChatRepository extends JpaRepository<Chat, Long> {
}
