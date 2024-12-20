DELIMITER $$
CREATE FUNCTION calculate_distance(lat1 DOUBLE, lon1 DOUBLE, lat2 DOUBLE, lon2 DOUBLE) RETURNS DOUBLE DETERMINISTIC NO SQL
BEGIN
DECLARE radius DOUBLE DEFAULT 6371;
DECLARE delta DOUBLE;
DECLARE lamda DOUBLE;
DECLARE a1 DOUBLE;
DECLARE a2 DOUBLE;

SET lat1 = RADIANS(lat1);
SET lat2 = RADIANS(lat2);
SET lon1 = RADIANS(lon1);
SET lon2 = RADIANS(lon2);

SET delta = ABS(lat1 - lat2);
SET lamda = ABS(lon1 - lon2);

SET a1 = POW(SIN(delta / 2), 2);
SET a2 = COS(lat1) * COS(lat2) * POW(SIN(lamda / 2), 2);

RETURN 2 * radius * ASIN(SQRT(a1 + a2));
END $$

DELIMITER ;