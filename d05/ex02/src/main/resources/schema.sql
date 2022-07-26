DROP SCHEMA IF EXISTS chat CASCADE;

CREATE SCHEMA chat;

CREATE TABLE IF NOT EXISTS chat.users (
    id BIGSERIAL PRIMARY KEY,
    login text UNIQUE NOT NULL,
    password text NOT NULL
);

CREATE TABLE IF NOT EXISTS chat.chatrooms (
    id BIGSERIAL PRIMARY KEY,
    name text UNIQUE NOT NULL,
    owner bigint REFERENCES chat.users(id)
);

CREATE TABLE IF NOT EXISTS chat.messages (
    id BIGSERIAL PRIMARY KEY,
    author bigint REFERENCES chat.users(id),
    room bigint REFERENCES chat.chatrooms(id),
    text text NOT NULL,
    timestamp timestamp NOT NULL
);