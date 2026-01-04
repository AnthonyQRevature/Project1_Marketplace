CREATE TABLE IF NOT EXISTS users
(
    user_id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS user_profiles
(
    --id SERIAL PRIMARY KEY
    user_id INT PRIMARY KEY REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS blocks
(
    blocker_id INT REFERENCES users(user_id),
    blocked_id INT REFERENCES users(user_id),
    PRIMARY KEY(blocker_id, blocked_id)
);

CREATE TABLE IF NOT EXISTS notifications
(
    notification_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id)
);

