CREATE TYPE user_role_enum AS ENUM ('user', 'admin', 'super');
CREATE TYPE notification_type_enum AS ENUM (
    'message_received',
    'system',
    'admin_action'
);

CREATE TYPE report_status_enum AS ENUM ('open', 'resolved');

CREATE TYPE post_status_enum AS ENUM ('available', 'unlisted', 'sold');

CREATE TYPE demerit_action_enum AS ENUM (
    'warning',
    'post_removed',
    'user_suspended',
    'user_deleted'
);

CREATE TYPE media_type_enum AS ENUM ('image', 'video');

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    role user_role_enum NOT NULL DEFAULT 'user',
    verified_seller BOOLEAN DEFAULT FALSE
    );

drop table user_profile;
drop table user_demerits;
drop table block;
drop table notifications;
drop table post_media;
drop table post_tags;
drop table tags;
drop table report;
drop table review;
drop table messages;
drop table post;
drop table users;
drop type media_type_enum;
drop type demerit_action_enum;
drop type post_status_enum;
drop type user_role_enum;
drop type notification_type_enum;
drop type report_status_enum;


CREATE TABLE IF NOT EXISTS user_profile (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    pfp_encoded TEXT,
    bio TEXT,
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6),
    address VARCHAR(255)
    );


CREATE TABLE IF NOT EXISTS block (
    blocker_id INT REFERENCES users(id) ON DELETE CASCADE,
    blocked_id INT REFERENCES users(id) ON DELETE CASCADE,
    PRIMARY KEY (blocker_id, blocked_id)
    );


CREATE TABLE IF NOT EXISTS notifications (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    type notification_type_enum NOT NULL,
    read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );


CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY,
    seller_id INT REFERENCES users(id) ON DELETE CASCADE,
    description TEXT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status post_status_enum DEFAULT 'unlisted',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_edit_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );


CREATE TABLE IF NOT EXISTS post_media (
    id SERIAL PRIMARY KEY,
    post_id INT REFERENCES post(id) ON DELETE CASCADE,
    media_encoded TEXT NOT NULL,
    media_type media_type_enum DEFAULT 'image'
    );


CREATE TABLE IF NOT EXISTS tags (
    id SERIAL PRIMARY KEY,
    tag_name VARCHAR(50) UNIQUE NOT NULL
    );


CREATE TABLE IF NOT EXISTS post_tags (
    post_id INT REFERENCES post(id) ON DELETE CASCADE,
    tag_id INT REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, tag_id)
    );


CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY,
    sender_id INT REFERENCES users(id) ON DELETE CASCADE,
    receiver_id INT REFERENCES users(id) ON DELETE CASCADE,
    post_id INT REFERENCES post(id) ON DELETE SET NULL,
    message_text TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP
    );


CREATE TABLE IF NOT EXISTS report (
    id SERIAL PRIMARY KEY,
    reporter_id INT REFERENCES users(id) ON DELETE CASCADE,
    reported_id INT REFERENCES users(id) ON DELETE CASCADE,
    post_id INT REFERENCES post(id) ON DELETE SET NULL,
    message_id INT REFERENCES messages(id) ON DELETE SET NULL,
    reason TEXT,
    status report_status_enum DEFAULT 'open'
    );


CREATE TABLE IF NOT EXISTS user_demerits (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    admin_id INT REFERENCES users(id) ON DELETE SET NULL,
    report_id INT REFERENCES report(id) ON DELETE SET NULL,
    reason TEXT NOT NULL,
    action demerit_action_enum NOT NULL
    );


CREATE TABLE IF NOT EXISTS review (
    id SERIAL PRIMARY KEY,
    reviewer_id INT REFERENCES users(id) ON DELETE CASCADE,
    seller_id INT REFERENCES users(id) ON DELETE CASCADE,
    post_id INT REFERENCES post(id) ON DELETE CASCADE,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

delete from post where seller_id = 2;
select * from post;