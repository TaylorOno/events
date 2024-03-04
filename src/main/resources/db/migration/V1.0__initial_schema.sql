CREATE TABLE event
(
    id        VARCHAR(255) NOT NULL,
    date_time TIMESTAMP,
    guests    INTEGER      NOT NULL,
    location  VARCHAR(255),
    title     VARCHAR(255),
    PRIMARY KEY (id)
);

INSERT INTO event (date_time, guests, location, title, id)
VALUES ('2022-03-15T12:00', 30, 'Ice Skating Rink', 'Michael Scott''s Birthday', 'a04228f1-368b-4692-a97a-2573c7ac735f');
INSERT INTO event (date_time, guests, location, title, id)
VALUES ('2022-09-22T18:00', 30, 'Chilis', 'The 5th Annual Dundies', 'b39379ac-f240-4a4c-87e1-76400643b25b');