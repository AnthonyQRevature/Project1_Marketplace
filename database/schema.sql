CREATE TABLE IF NOT EXISTS users
(
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(10) DEFAULT 'USER',
    verified_seller BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS user_profiles
(
    user_id INT PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    bio TEXT,
    profile_picture_url VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS blocks
(
    blocked_by_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    blocked_user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    PRIMARY KEY(blocked_by_id, blocked_user_id)
);

CREATE TABLE IF NOT EXISTS notifications
(
    notification_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

