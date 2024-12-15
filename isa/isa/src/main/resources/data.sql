insert into users(email, name, surname, address, username, password, role) values ('marko@example.com', 'Marko', 'Marković', 'Ulica Marka 1', 'marko', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 0);
insert into users (email, name, surname, address, username, password, role) values('milan@example.com', 'Milan', 'Milanović', 'Ulica Milana 2', 'milan2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (email, name, surname, address, username, password, role) values ('ivana@example.com', 'Ivana', 'Ivanović', 'Ulica Ivane 3', 'ivana2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (email, name, surname, address, username, password, role) values ('bojan@example.com', 'Bojan', 'Bojanović', 'Ulica Bojana 4', 'bojan2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (email, name, surname, address, username, password, role) values ('pera@example.com', 'Pera', 'Perić', 'Ulica Pere 5', 'pera2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (email, name, surname, address, username, password, role) values ('zoran@example.com', 'Zoran', 'Zoranović', 'Ulica Zorana 6', 'zoran2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (email, name, surname, address, username, password, role) values ('bojana@example.com', 'Bojana', 'Bojanović', 'Ulica Bojane 7', 'bojana2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (email, name, surname, address, username, password, role) values ('milana@example.com', 'Milana', 'Milanović', 'Ulica Milane 8', 'milana2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);
insert into users (email, name, surname, address, username, password, role) values ('jovana@example.com', 'Jovana', 'Jovanić', 'Ulica Jovane 9', 'jovana2014', '$2a$10$AfamkYjZEgyFSDqzlj/QLeN13GcmvFhG/XMmQZQONq/1F8iLTSC92', 1);


insert into location (latitude, longitude, address) values (44.8176, 20.4633, 'Beograd, Srbija');
insert into location (latitude, longitude, address) values (45.2671, 19.8335, 'Novi Sad, Srbija');
insert into location (latitude, longitude, address) values (43.8486, 18.3564, 'Sarajevo, Bosna i Hercegovina');
insert into location (latitude, longitude, address) values (42.6611, 21.1655, 'Skoplje, Severna Makedonija');
insert into location (latitude, longitude, address) values (46.0511, 14.5051, 'Ljubljana, Slovenija');


insert into post (creation_time, location_id, user_id, description, image_path) values ('2024-11-10 10:00:00', 1, 7, 'Post description for Bojana', '/images/bunny1.jpeg');
insert into post (creation_time, location_id, user_id, description, image_path) values ('2024-11-10 11:00:00', 2, 8, 'Post description for Milana', '/images/bunny2.jpg');
insert into post (creation_time, location_id, user_id, description, image_path) values ('2024-11-10 12:00:00', 3, 9, 'Post description for Jovana', '/images/bunny3.jpg');
insert into post (creation_time, location_id, user_id, description, image_path) values ('2024-11-10 13:00:00', 4, 7, 'Post description for Bojana 2', '/images/bunny4.jpg');
insert into post (creation_time, location_id, user_id, description, image_path) values ('2024-11-10 14:00:00', 5, 6, 'Post description for Zoran', '/images/bunny5.jpg');
insert into post (creation_time, location_id, user_id, description, image_path) values ('2024-11-10 15:00:00', 1, 5, 'Post description for Pera', '/images/bunny6.jpg');
insert into post (creation_time, location_id, user_id, description, image_path) values ('2024-11-10 15:00:00', 1, 2, 'Post description for Milan', '/images/bunny1.jpeg');
insert into post (creation_time, location_id, user_id, description, image_path) values ('2024-11-10 15:00:00', 1, 3, 'Post description for Ivana', '/images/bunny2.jpg');

insert into likes (post_id, user_id) values (1, 8);
insert into likes (post_id, user_id) values (2, 9);
insert into likes (post_id, user_id) values (3, 7);
insert into likes (post_id, user_id) values (4, 8);
insert into likes (post_id, user_id) values (4, 9);
insert into likes (post_id, user_id) values (4, 6);
insert into likes (post_id, user_id) values (5, 7);
insert into likes (post_id, user_id) values (5, 8);
insert into likes (post_id, user_id) values (5, 9);
insert into likes (post_id, user_id) values (6, 7);
insert into likes (post_id, user_id) values (6, 8);
insert into likes (post_id, user_id) values (6, 9);
insert into likes (post_id, user_id) values (4, 7);
insert into likes (post_id, user_id) values (4, 5);
insert into likes (post_id, user_id) values (4, 4);



insert into comment (post_id, "timestamp", user_id, content) values (1, '2024-11-10 10:05:00', 8, 'This is a comment');
insert into comment (post_id, "timestamp", user_id, content) values (2, '2024-11-10 11:05:00', 9, 'This is a comment');
insert into comment (post_id, "timestamp", user_id, content) values (3, '2024-11-10 12:05:00', 7, 'This is a comment');
insert into comment (post_id, "timestamp", user_id, content) values (1, '2024-11-10 10:10:00', 9, 'Great post!');
insert into comment (post_id, "timestamp", user_id, content) values (1, '2024-11-10 10:15:00', 7, 'Interesting thought!');
insert into comment (post_id, "timestamp", user_id, content) values (2, '2024-11-10 11:10:00', 8, 'Amazing content!');
insert into comment (post_id, "timestamp", user_id, content) values (2, '2024-11-10 11:20:00', 7, 'Very informative!');
insert into comment (post_id, "timestamp", user_id, content) values (3, '2024-11-10 12:10:00', 8, 'Nice post!');
insert into comment (post_id, "timestamp", user_id, content) values (3, '2024-11-10 12:15:00', 9, 'Keep it up!');
insert into comment (post_id, "timestamp", user_id, content) values (1, '2024-11-10 10:20:00', 8, 'Thanks for sharing!');
insert into comment (post_id, "timestamp", user_id, content) values (2, '2024-11-10 11:25:00', 9, 'Really cool!');
insert into comment (post_id, "timestamp", user_id, content) values (3, '2024-11-10 12:20:00', 7, 'Very helpful!');



insert into follower(user_id, followed_user_id) values (1, 2);
insert into follower(user_id, followed_user_id) values (1, 3);
insert into follower(user_id, followed_user_id) values (1, 4);
insert into follower(user_id, followed_user_id) values (1, 5);
insert into follower(user_id, followed_user_id) values (3, 2);
insert into follower(user_id, followed_user_id) values (3, 7);
insert into follower(user_id, followed_user_id) values (4, 2);
insert into follower(user_id, followed_user_id) values (5, 2);
insert into follower(user_id, followed_user_id) values (6, 2);
insert into follower(user_id, followed_user_id) values (7, 2);
insert into follower(user_id, followed_user_id) values (8, 2);
