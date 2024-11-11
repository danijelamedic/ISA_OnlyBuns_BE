insert into public.users (id, email, name, surname, username) values (1, 'marko@example.com', 'Marko', 'Marković', 'marko');
insert into public.users (id, email, name, surname, username) values (2, 'milan@example.com', 'Milan', 'Milanović', 'milan2014');
insert into public.users (id, email, name, surname, username) values (3, 'ivana@example.com', 'Ivana', 'Ivanović', 'ivana2014');
insert into public.users (id, email, name, surname, username) values (4, 'bojan@example.com', 'Bojan', 'Bojanović', 'bojan2014');
insert into public.users (id, email, name, surname, username) values (5, 'pera@example.com', 'Pera', 'Perić', 'pera2014');
insert into public.users (id, email, name, surname, username) values (6, 'zoran@example.com', 'Zoran', 'Zoranović', 'zoran2014');
insert into public.users (id, email, name, surname, username) values (7, 'bojana@example.com', 'Bojana', 'Bojanović', 'bojana2014');
insert into public.users (id, email, name, surname, username) values (8, 'milana@example.com', 'Milana', 'Milanović', 'milana2014');
insert into public.users (id, email, name, surname, username) values (9, 'jovana@example.com', 'Jovana', 'Jovanić', 'jovana2014');


insert into public.locations (id, latitude, longitude, address) values (1, 44.8176, 20.4633, 'Beograd, Srbija');
insert into public.locations (id, latitude, longitude, address) values (2, 45.2671, 19.8335, 'Novi Sad, Srbija');
insert into public.locations (id, latitude, longitude, address) values (3, 43.8486, 18.3564, 'Sarajevo, Bosna i Hercegovina');
insert into public.locations (id, latitude, longitude, address) values (4, 42.6611, 21.1655, 'Skoplje, Severna Makedonija');
insert into public.locations (id, latitude, longitude, address) values (5, 46.0511, 14.5051, 'Ljubljana, Slovenija');


insert into public.posts (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 10:00:00', 1, 1, 7, 'Post description for Bojana', 'path/to/image1.jpg');
insert into public.posts (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 11:00:00', 2, 2, 8, 'Post description for Milana', 'path/to/image2.jpg');
insert into public.posts (creation_time, id, location_id, user_id, description, image_path) values ('2024-11-10 12:00:00', 3, 3, 9, 'Post description for Jovana', 'path/to/image3.jpg');


insert into public.likes (id, post_id, user_id) values (1, 1, 8);
insert into public.likes (id, post_id, user_id) values (2, 2, 9);
insert into public.likes (id, post_id, user_id) values (3, 3, 7);


insert into public.comments (id, post_id, "timestamp", user_id, content) values (1, 1, '2024-11-10 10:05:00', 8, 'This is a comment');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (2, 2, '2024-11-10 11:05:00', 9, 'This is a comment');
insert into public.comments (id, post_id, "timestamp", user_id, content) values (3, 3, '2024-11-10 12:05:00', 7, 'This is a comment');
