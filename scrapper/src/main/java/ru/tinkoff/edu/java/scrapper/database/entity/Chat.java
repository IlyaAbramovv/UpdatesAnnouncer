package ru.tinkoff.edu.java.scrapper.database.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "chat")
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
public class Chat {
    public long id() {
        return getId();
    }

    public Chat(long id) {
        this.id = id;
    }

    @Id
    @Column(name = "chat_id")
    private long id;

    @ManyToMany
    @JoinTable(
            name = "chat_link",
            joinColumns = {@JoinColumn(name = "chat_id")},
            inverseJoinColumns = {@JoinColumn(name = "link_id")}
    )
    Set<Link> links;
}
