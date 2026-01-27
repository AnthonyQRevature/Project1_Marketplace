CREATE FUNCTION radians(DOUBLE PRECISION) RETURNS DOUBLE PRECISION AS $$
    SELECT $1 * PI() / 180;
$$ LANGUAGE SQL

CREATE FUNCTION haversine_formula(lat1 DOUBLE PRECISION, lon1 DOUBLE PRECISION, lat2 DOUBLE PRECISION, lon2 DOUBLE PRECISION) RETURNS DOUBLE PRECISION AS $$
    SELECT 2*ear_rad*ASIN(SQRT(s1 * s1 + COS(radians(lat1)) * COS(radians(lat2)) * s2 * s2)) FROM (
        SELECT 
            SIN((radians(lat2) - radians(lat1)) / 2) AS s1, 
            SIN((radians(lon2) - radians(lon1)) / 2) AS s2, 
            3959 AS ear_rad
    );
$$ LANGUAGE SQL

CREATE VIEW user_distances AS (
    SELECT users.id, users.username, user_profile.pfp_encoded, user_profile.latitude, user_profile.longitude
    FROM users INNER JOIN user_profile ON users.id = user_profile.id

);

CREATE VIEW conversations AS (
    SELECT sender, reciever, username, user_profile.pfp_encoded FROM 
    (
        SELECT DISTINCT messages.sender_id AS sender, messages.receiver_id AS reciever
        FROM messages
        UNION
        SELECT DISTINCT messages.receiver_id AS sender, messages.sender_id AS reciever
        FROM messages
    ) INNER JOIN users ON reciever = users.id
    INNER JOIN user_profile ON users.id = user_profile.user_id
    WHERE (sender, reciever) NOT IN (
        SELECT blocker_id, blocked_id FROM block
    )
);
