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

INSERT INTO report(reporter_id, reported_id, reason) VALUES
(1, 2, 'Stinky!')
