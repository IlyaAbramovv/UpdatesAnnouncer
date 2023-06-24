package ru.tinkoff.edu.java.scrapper.database.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "link")
@NoArgsConstructor
@EqualsAndHashCode(of = "url")
@Accessors(chain = true)
public class Link {
    public long id() {
        return getId();
    }

    public String url() {
        return getUrl();
    }

    public Instant lastUpdate() {
        return getLastUpdate();
    }

    public Link(long id, String url, Instant lastUpdate) {
        this.id = id;
        this.url = url;
        this.lastUpdate = lastUpdate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    long id;

    @Column(name = "url")
    String url;

    @Column(name = "updated_at")
    Instant lastUpdate;

    @ManyToMany(mappedBy = "links")
    private Set<Chat> chats;
}
