INSERT INTO tags(tag_name) VALUES
('Shirt'),
('Pants'),
('Shoes'),
('Dresses'),
('Unused'),
('Used');

INSERT INTO users(id, email, password_hash, username) VALUES
(1, 'joes@email', 'test', 'BigJoe'),
(2, 'janes@email', 'test', 'FancyJane');
INSERT INTO users(id, email, password_hash, username) VALUES
(3, 'dougs@email', 'test', 'doog');

INSERT INTO user_profile(user_id, latitude, longitude) VALUES
(1, 123, 122),
(2, 102, 221);

INSERT INTO post(id, seller_id, description, price, status) VALUES
(1, 3, 'A big shirt', 50.00, 'available'),
(2, 3, 'my diamond sword', 990.00, 'available');

INSERT INTO post_media(post_id, media_encoded) VALUES
(1, 'abc'),
(1, 'def'),
(2, 'ghi');

INSERT INTO report(reporter_id, reported_id, reason) VALUES
(1, 2, 'Stinky!')

select * from report

CREATE TABLE IF NOT EXISTS report (
    id SERIAL PRIMARY KEY,
    reporter_id INT REFERENCES users(id) ON DELETE CASCADE,
    reported_id INT REFERENCES users(id) ON DELETE CASCADE,
    post_id INT REFERENCES post(id) ON DELETE SET NULL,
    message_id INT REFERENCES messages(id) ON DELETE SET NULL,
    reason TEXT,
    status report_status_enum DEFAULT 'open'
    );