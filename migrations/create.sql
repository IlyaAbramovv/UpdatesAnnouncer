create table link
(
    link_id    serial primary key,
    url        text,
    updated_at timestamp
);

create table chat
(
    chat_id int primary key
);

create table chat_link
(
    chat_link_id serial primary key,
    chat_id      int,
    link_id      int,
    foreign key (chat_id) references chat (chat_id) on delete cascade,
    foreign key (link_id) references link (link_id) on delete cascade
);

