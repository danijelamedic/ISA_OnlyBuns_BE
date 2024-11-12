insert into users (id, email, name, surname, username) values (1, 'marko@example.com', 'Marko', 'Marković', 'marko');
insert into users (id, email, name, surname, username) values (2, 'milan@example.com', 'Milan', 'Milanović', 'milan2014');
insert into users (id, email, name, surname, username) values (3, 'ivana@example.com', 'Ivana', 'Ivanović', 'ivana2014');
insert into users (id, email, name, surname, username) values (4, 'bojan@example.com', 'Bojan', 'Bojanović', 'bojan2014');
insert into users (id, email, name, surname, username) values (5, 'pera@example.com', 'Pera', 'Perić', 'pera2014');
insert into users (id, email, name, surname, username) values (6, 'zoran@example.com', 'Zoran', 'Zoranović', 'zoran2014');
insert into users (id, email, name, surname, username) values (7, 'bojana@example.com', 'Bojana', 'Bojanović', 'bojana2014');
insert into users (id, email, name, surname, username) values (8, 'milana@example.com', 'Milana', 'Milanović', 'milana2014');
insert into users (id, email, name, surname, username) values (9, 'jovana@example.com', 'Jovana', 'Jovanić', 'jovana2014');


insert into locations (id, latitude, longitude, address) values (1, 44.8176, 20.4633, 'Beograd, Srbija');
insert into locations (id, latitude, longitude, address) values (2, 45.2671, 19.8335, 'Novi Sad, Srbija');
insert into locations (id, latitude, longitude, address) values (3, 43.8486, 18.3564, 'Sarajevo, Bosna i Hercegovina');
insert into locations (id, latitude, longitude, address) values (4, 42.6611, 21.1655, 'Skoplje, Severna Makedonija');
insert into locations (id, latitude, longitude, address) values (5, 46.0511, 14.5051, 'Ljubljana, Slovenija');


insert into public.posts (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 10:00:00', 1, 1, 7, 'Post description for Bojana', '/images/bunny1.jpeg');
insert into public.posts (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 11:00:00', 2, 2, 8, 'Post description for Milana', '/images/bunny2.jpg');
insert into public.posts (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 12:00:00', 3, 3, 9, 'Post description for Jovana', '/images/bunny3.jpg');
insert into public.posts (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 13:00:00', 4, 4, 7, 'Post description for Bojana 2', '/images/bunny4.jpg');
insert into public.posts (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 14:00:00', 5, 5, 6, 'Post description for Zoran', '/images/bunny5.jpg');
insert into public.posts (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 15:00:00', 6, 1, 5, 'Post description for Pera', '/images/bunny6.jpg');

insert into public.likes (id, post_id, user_id) values (1, 1, 8);
insert into public.likes (id, post_id, user_id) values (2, 2, 9);
insert into public.likes (id, post_id, user_id) values (3, 3, 7);
insert into public.likes (id, post_id, user_id) values (4, 4, 8);
insert into public.likes (id, post_id, user_id) values (5, 4, 9);
insert into public.likes (id, post_id, user_id) values (6, 4, 6);
insert into public.likes (id, post_id, user_id) values (7, 5, 7);
insert into public.likes (id, post_id, user_id) values (8, 5, 8);
insert into public.likes (id, post_id, user_id) values (9, 5, 9);
insert into public.likes (id, post_id, user_id) values (10, 6, 7);
insert into public.likes (id, post_id, user_id) values (11, 6, 8);
insert into public.likes (id, post_id, user_id) values (12, 6, 9);
insert into public.likes (id, post_id, user_id) values (13, 4, 7);
insert into public.likes (id, post_id, user_id) values (14, 4, 5);
insert into public.likes (id, post_id, user_id) values (15, 4, 4);


insert into public.comments (id, post_id, "timestamp", user_id, content) values (1, 1, '2024-11-10 10:05:00', 8, 'This is a comment');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (2, 2, '2024-11-10 11:05:00', 9, 'This is a comment');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (3, 3, '2024-11-10 12:05:00', 7, 'This is a comment');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (4, 1, '2024-11-10 10:10:00', 9, 'Great post!');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (5, 1, '2024-11-10 10:15:00', 7, 'Interesting thought!');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (6, 2, '2024-11-10 11:10:00', 8, 'Amazing content!');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (7, 2, '2024-11-10 11:20:00', 7, 'Very informative!');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (8, 3, '2024-11-10 12:10:00', 8, 'Nice post!');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (9, 3, '2024-11-10 12:15:00', 9, 'Keep it up!');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (10, 1, '2024-11-10 10:20:00', 8, 'Thanks for sharing!');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (11, 2, '2024-11-10 11:25:00', 9, 'Really cool!');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (12, 3, '2024-11-10 12:20:00', 7, 'Very helpful!');
