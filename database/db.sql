SELECT add(1, 2);

SELECT sine_of(radians(90));

SELECT haversine_formula(-1.7, -0.16, 1, 4.62);
DROP FUNCTION haversine_formula;
DROP VIEW userdistances

SELECT * FROM (
    SELECT users.id, users.username, haversine_formula(user_profile.latitude, user_profile.longitude, 33.03810434840791, -96.69287478499572) AS estimated_distance
    FROM users INNER JOIN user_profile ON users.id = user_profile.user_id
    ORDER BY estimated_distance ASC
)
WHERE estimated_distance < 5;