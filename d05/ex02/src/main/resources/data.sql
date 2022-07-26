INSERT INTO chat.users (login, password) VALUES ('Bob', '12345678');
INSERT INTO chat.users (login, password) VALUES ('Bill', 'abcd');
INSERT INTO chat.users (login, password) VALUES ('Mary', 'helloworld');
INSERT INTO chat.users (login, password) VALUES ('Ann', '000000');
INSERT INTO chat.users (login, password) VALUES ('John', 'abcd000000');

INSERT INTO chat.chatrooms (name, owner) VALUES ('talks', 1);
INSERT INTO chat.chatrooms (name, owner) VALUES ('general', 2);
INSERT INTO chat.chatrooms (name, owner) VALUES ('random', 3);
INSERT INTO chat.chatrooms (name, owner) VALUES ('42_code', 4);
INSERT INTO chat.chatrooms (name, owner) VALUES ('support', 5);

INSERT INTO chat.messages (author, room, text, timestamp)
    VALUES (2, 1, 'Hello World!', CURRENT_TIMESTAMP);
INSERT INTO chat.messages (author, room, text, timestamp)
    VALUES (3, 1, 'Hi, Bill!', CURRENT_TIMESTAMP);
INSERT INTO chat.messages (author, room, text, timestamp)
    VALUES (1, 3, 'LOL', CURRENT_TIMESTAMP);
INSERT INTO chat.messages (author, room, text, timestamp)
    VALUES (4, 2, 'HELLO_WORLD', CURRENT_TIMESTAMP);
INSERT INTO chat.messages (author, room, text, timestamp)
    VALUES (5, 5, '\\report', CURRENT_TIMESTAMP);
