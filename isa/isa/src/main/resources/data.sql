insert into users(id, email, name, surname, address, username, password, role) values (1, 'marko@example.com', 'Marko', 'Marković', 'Ulica Marka 1', 'marko', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 0);
insert into users (id, email, name, surname, address, username, password, role) values (2, 'milan@example.com', 'Milan', 'Milanović', 'Ulica Milana 2', 'milan2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (id, email, name, surname, address, username, password, role) values (3, 'ivana@example.com', 'Ivana', 'Ivanović', 'Ulica Ivane 3', 'ivana2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (id, email, name, surname, address, username, password, role) values (4, 'bojan@example.com', 'Bojan', 'Bojanović', 'Ulica Bojana 4', 'bojan2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (id, email, name, surname, address, username, password, role) values (5, 'pera@example.com', 'Pera', 'Perić', 'Ulica Pere 5', 'pera2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (id, email, name, surname, address, username, password, role) values (6, 'zoran@example.com', 'Zoran', 'Zoranović', 'Ulica Zorana 6', 'zoran2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (id, email, name, surname, address, username, password, role) values (7, 'bojana@example.com', 'Bojana', 'Bojanović', 'Ulica Bojane 7', 'bojana2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (id, email, name, surname, address, username, password, role) values (8, 'milana@example.com', 'Milana', 'Milanović', 'Ulica Milane 8', 'milana2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (id, email, name, surname, address, username, password, role) values (9, 'jovana@example.com', 'Jovana', 'Jovanić', 'Ulica Jovane 9', 'jovana2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);


insert into location (id, latitude, longitude, address) values (1, 44.8176, 20.4633, 'Beograd, Srbija');
insert into location (id, latitude, longitude, address) values (2, 45.2671, 19.8335, 'Novi Sad, Srbija');
insert into location (id, latitude, longitude, address) values (3, 43.8486, 18.3564, 'Sarajevo, Bosna i Hercegovina');
insert into location (id, latitude, longitude, address) values (4, 42.6611, 21.1655, 'Skoplje, Severna Makedonija');
insert into location (id, latitude, longitude, address) values (5, 46.0511, 14.5051, 'Ljubljana, Slovenija');


insert into post (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 10:00:00', 1, 1, 7, 'Post description for Bojana', '/images/bunny1.jpeg');
insert into post (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 11:00:00', 2, 2, 8, 'Post description for Milana', '/images/bunny2.jpg');
insert into post (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 12:00:00', 3, 3, 9, 'Post description for Jovana', '/images/bunny3.jpg');
insert into post (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 13:00:00', 4, 4, 7, 'Post description for Bojana 2', '/images/bunny4.jpg');
insert into post (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 14:00:00', 5, 5, 6, 'Post description for Zoran', '/images/bunny5.jpg');
insert into post (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 15:00:00', 6, 1, 5, 'Post description for Pera', '/images/bunny6.jpg');

insert into likes (id, post_id, user_id) values (1, 1, 8);
insert into likes (id, post_id, user_id) values (2, 2, 9);
insert into likes (id, post_id, user_id) values (3, 3, 7);
insert into likes (id, post_id, user_id) values (4, 4, 8);
insert into likes (id, post_id, user_id) values (5, 4, 9);
insert into likes (id, post_id, user_id) values (6, 4, 6);
insert into likes (id, post_id, user_id) values (7, 5, 7);
insert into likes (id, post_id, user_id) values (8, 5, 8);
insert into likes (id, post_id, user_id) values (9, 5, 9);
insert into likes (id, post_id, user_id) values (10, 6, 7);
insert into likes (id, post_id, user_id) values (11, 6, 8);
insert into likes (id, post_id, user_id) values (12, 6, 9);
insert into likes (id, post_id, user_id) values (13, 4, 7);
insert into likes (id, post_id, user_id) values (14, 4, 5);
insert into likes (id, post_id, user_id) values (15, 4, 4);


insert into comment (id, post_id, "timestamp", user_id, content) values (1, 1, '2024-11-10 10:05:00', 8, 'This is a comment');
insert into comment (id, post_id, "timestamp", user_id, content) values (2, 2, '2024-11-10 11:05:00', 9, 'This is a comment');
insert into comment (id, post_id, "timestamp", user_id, content) values (3, 3, '2024-11-10 12:05:00', 7, 'This is a comment');
insert into comment (id, post_id, "timestamp", user_id, content) values (4, 1, '2024-11-10 10:10:00', 9, 'Great post!');
insert into comment (id, post_id, "timestamp", user_id, content) values (5, 1, '2024-11-10 10:15:00', 7, 'Interesting thought!');
insert into comment (id, post_id, "timestamp", user_id, content) values (6, 2, '2024-11-10 11:10:00', 8, 'Amazing content!');
insert into comment (id, post_id, "timestamp", user_id, content) values (7, 2, '2024-11-10 11:20:00', 7, 'Very informative!');
insert into comment (id, post_id, "timestamp", user_id, content) values (8, 3, '2024-11-10 12:10:00', 8, 'Nice post!');
insert into comment (id, post_id, "timestamp", user_id, content) values (9, 3, '2024-11-10 12:15:00', 9, 'Keep it up!');
insert into comment (id, post_id, "timestamp", user_id, content) values (10, 1, '2024-11-10 10:20:00', 8, 'Thanks for sharing!');
insert into comment (id, post_id, "timestamp", user_id, content) values (11, 2, '2024-11-10 11:25:00', 9, 'Really cool!');
insert into comment (id, post_id, "timestamp", user_id, content) values (12, 3, '2024-11-10 12:20:00', 7, 'Very helpful!');
