INSERT INTO tags(tag_name) VALUES
('Shirt'),
('Pants'),
('Shoes'),
('Dresses'),
('Unused'),
('Used');

INSERT INTO users(email, password_hash, username) VALUES
('joes@email', 'test', 'BigJoe'),
('janes@email', 'test', 'FancyJane');
INSERT INTO users(email, password_hash, username) VALUES
('dougs@email', 'test', 'doog');

INSERT INTO user_profile(user_id, latitude, longitude) VALUES
(1, 123, 122),
(2, 102, 221);



INSERT INTO post(seller_id, description, price, status) VALUES
(2, 'A big shirt', 50.00, 'available'),
(2, 'my diamond sword', 990.00, 'available');

insert into post_tags(post_id, tag_id) VALUES
(3, 1);

INSERT INTO report(reporter_id, reported_id, reason) VALUES
(1, 2, 'Stinky!');

Insert into post_media (post_id, media_encoded) VALUES
(3, 'media1.jpg'),
(4, 'media3.jpg');

Insert into post_media (post_id, media_encoded) VALUES
(3, 'media1.jpg'),
(4, 'media3.jpg');

update post_media
set media_encoded = 'media1.png'
where post_id = 3;

update post
set description='the best tie'
where id = 4;

TRUNCATE table post_media;

select * from tags;
select * from post;
select * from post_tags;
select * from users;
select * from post_media;
select * from user_profile;

update user_profile
set bio='Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
where id =1;