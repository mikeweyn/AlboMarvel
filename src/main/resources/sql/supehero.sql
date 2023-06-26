DROP TABLE IF EXISTS SUPERHERO;

CREATE TABLE SUPERHERO
(
    id          INT,
    name        VARCHAR(250) NOT NULL,
    description VARCHAR(500) NOT NULL,
    last_sync  TIMESTAMP (3)

);

DROP TABLE IF EXISTS COLABORATORS;

CREATE TABLE COLABORATORS
(
    /*last_sync timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),*/
    id        INT,
    name      VARCHAR(250) NOT NULL,
    role      VARCHAR(250) NOT NULL

);


CREATE TABLE CHARACTERS
(
    /*last_sync timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),*/
    id          INT,
    id_comic    INT,
    character   VARCHAR(250) NOT NULL,
    comics      VARCHAR(250) NOT NULL

);


