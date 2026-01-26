SELECT add(1, 2);

SELECT sine_of(radians(90));

SELECT haversine_formula(-1.7, -0.16, 1, 4.62);
DROP FUNCTION haversine_formula;


SELECT * FROM (
    SELECT users.id, users.username, haversine_formula(user_profile.latitude, user_profile.longitude, 33.03810434840791, -96.69287478499572) AS estimated_distance
    FROM users INNER JOIN user_profile ON users.id = user_profile.user_id
    ORDER BY estimated_distance ASC
)
WHERE estimated_distance < 5;

SELECT sender, reciever, username, user_profile.id FROM 
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
);

DROP VIEW conversations;
SELECT * FROM conversations;