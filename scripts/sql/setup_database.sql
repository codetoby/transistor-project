USE gtfs;

DROP TABLE IF EXISTS zipcodes;
DROP TABLE IF EXISTS transfers;
DROP TABLE IF EXISTS stop_times;
DROP TABLE IF EXISTS trips;
DROP TABLE IF EXISTS stops;
DROP TABLE IF EXISTS shapes;
DROP TABLE IF EXISTS routes;
DROP TABLE IF EXISTS feed;
DROP TABLE IF EXISTS calendar_dates;
DROP TABLE IF EXISTS agency;

CREATE TABLE
  `zipcodes` (
    `zipcode_code` varchar(45) NOT NULL,
    `zipcode_lat` varchar(45) NOT NULL,
    `zipcode_lon` varchar(45) NOT NULL,
    PRIMARY KEY (`zipcode_code`),
    UNIQUE KEY `zipcode_code_UNIQUE` (`zipcode_code`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE
  `agency` (
    `agency_id` varchar(45) NOT NULL,
    `agency_name` varchar(45) NOT NULL,
    `agency_url` varchar(45) NOT NULL,
    `agency_timezone` varchar(45) NOT NULL,
    `agency_phone` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`agency_id`),
    UNIQUE KEY `agency_id_UNIQUE` (`agency_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE
  `calendar_dates` (
    `service_id` int NOT NULL,
    `date` varchar(45) NOT NULL,
    `exception_type` int NOT NULL,
    PRIMARY KEY (`service_id`, `date`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE
  `feed` (
    `feed_publisher_name` varchar(45) NOT NULL,
    `feed_id` varchar(45) DEFAULT NULL,
    `feed_publisher_url` varchar(45) NOT NULL,
    `feed_lang` varchar(45) NOT NULL,
    `feed_start_date` varchar(45) DEFAULT NULL,
    `feed_end_date` varchar(45) DEFAULT NULL,
    `feed_version` varchar(45) DEFAULT NULL
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE
  `routes` (
    `route_id` varchar(45) NOT NULL,
    `agency_id` varchar(45) NOT NULL,
    `route_short_name` varchar(45) DEFAULT NULL,
    `route_long_name` varchar(45) DEFAULT NULL,
    `route_desc` varchar(45) DEFAULT NULL,
    `route_type` int NOT NULL,
    `route_color` varchar(45) DEFAULT NULL,
    `route_text_color` varchar(45) DEFAULT NULL,
    `route_url` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`route_id`),
    UNIQUE KEY `route_id_UNIQUE` (`route_id`),
    KEY `agency_id_idx` (`agency_id`),
    CONSTRAINT `fk_routes_agency_id` FOREIGN KEY (`agency_id`) REFERENCES `agency` (`agency_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

  CREATE TABLE
  `shapes` (
    `shape_id` varchar(45) NOT NULL,
    `shape_pt_sequence` varchar(45) NOT NULL,
    `shape_pt_lat` varchar(45) NOT NULL,
    `shape_pt_lon` varchar(45) NOT NULL,
    `shape_dist_traveled` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`shape_id`, `shape_pt_sequence`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

  CREATE TABLE
  `stops` (
    `stop_id` varchar(45) NOT NULL,
    `stop_code` varchar(45) DEFAULT NULL,
    `stop_name` varchar(45) DEFAULT NULL,
    `stop_lat` varchar(45) DEFAULT NULL,
    `stop_lon` varchar(45) DEFAULT NULL,
    `location_type` varchar(45) DEFAULT NULL,
    `parent_station` varchar(45) DEFAULT NULL,
    `stop_timezone` varchar(45) DEFAULT NULL,
    `wheelchair_boarding` int DEFAULT NULL,
    `platform_code` varchar(45) DEFAULT NULL,
    `zone_id` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`stop_id`),
    UNIQUE KEY `stop_id_UNIQUE` (`stop_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

  CREATE TABLE
  `trips` (
    `route_id` varchar(45) NOT NULL,
    `service_id` varchar(45) NOT NULL,
    `trip_id` varchar(45) NOT NULL,
    `realtime_trip_id` varchar(45) DEFAULT NULL,
    `trip_headsign` varchar(45) DEFAULT NULL,
    `trip_short_name` varchar(45) DEFAULT NULL,
    `trips_long_name` varchar(45) DEFAULT NULL,
    `direction_id` int DEFAULT NULL,
    `block_id` varchar(45) DEFAULT NULL,
    `shape_id` varchar(45) NOT NULL,
    `wheelchair_accessible` int DEFAULT NULL,
    `bikes_allowed` int DEFAULT '0',
    PRIMARY KEY (`trip_id`),
    UNIQUE KEY `trip_id_UNIQUE` (`trip_id`),
    KEY `fk_trips_route_id_idx` (`route_id`),
    KEY `fk_trips_shape_id_idx` (`shape_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

  CREATE TABLE
  `stop_times` (
    `trip_id` varchar(45) NOT NULL,
    `stop_sequence` varchar(45) NOT NULL,
    `stop_id` varchar(45) NOT NULL,
    `stop_headsign` varchar(45) DEFAULT NULL,
    `arrival_time` varchar(45) DEFAULT NULL,
    `departure_time` varchar(45) DEFAULT NULL,
    `pickup_type` int DEFAULT NULL,
    `drop_off_type` int DEFAULT NULL,
    `timepoint` int DEFAULT NULL,
    `shape_dist_traveled` float DEFAULT NULL,
    `fare_units_traveled` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`trip_id`, `stop_sequence`),
    KEY `fk_stope_time_stopId_idx` (`stop_id`),
    CONSTRAINT `fk_stope_time_stopId` FOREIGN KEY (`stop_id`) REFERENCES `stops` (`stop_id`),
    CONSTRAINT `fk_stope_trip_id` FOREIGN KEY (`trip_id`) REFERENCES `trips` (`trip_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

  CREATE TABLE
  `transfers` (
    `from_stop_id` varchar(45) NOT NULL,
    `to_stop_id` varchar(45) NOT NULL,
    `from_route_id` varchar(45) NOT NULL,
    `to_route_id` varchar(45) NOT NULL,
    `from_trip_id` varchar(45) NOT NULL,
    `to_trip_id` varchar(45) NOT NULL,
    `transfer_type` int NOT NULL
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

LOAD DATA LOCAL INFILE 'CompleteZipCodes.txt' INTO TABLE zipcodes FIELDS TERMINATED BY ',' IGNORE 1 LINES;
LOAD DATA LOCAL INFILE 'gtfs/agency.txt' INTO TABLE agency FIELDS TERMINATED BY ',' IGNORE 1 LINES;
LOAD DATA LOCAL INFILE 'gtfs/calendar_dates.txt' INTO TABLE calendar_dates FIELDS TERMINATED BY ',' IGNORE 1 LINES;
LOAD DATA LOCAL INFILE 'gtfs/feed_info.txt' INTO TABLE feed FIELDS TERMINATED BY ',' IGNORE 1 LINES;
LOAD DATA LOCAL INFILE 'gtfs/routes.txt' INTO TABLE routes FIELDS TERMINATED BY ',' IGNORE 1 LINES;
LOAD DATA LOCAL INFILE 'gtfs/shapes.txt' INTO TABLE shapes FIELDS TERMINATED BY ',' IGNORE 1 LINES;
LOAD DATA LOCAL INFILE 'gtfs/stops.txt' INTO TABLE stops FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' IGNORE 1 LINES;
LOAD DATA LOCAL INFILE 'gtfs/trips.txt' INTO TABLE trips FIELDS TERMINATED BY ',' IGNORE 1 LINES;
LOAD DATA LOCAL INFILE 'gtfs/stop_times.txt' INTO TABLE stop_times FIELDS TERMINATED BY ',' IGNORE 1 LINES;
LOAD DATA LOCAL INFILE 'gtfs/transfers.txt' INTO TABLE transfers FIELDS TERMINATED BY ',' IGNORE 1 LINES;

ALTER TABLE shapes MODIFY COLUMN shape_pt_sequence INT;

DROP FUNCTION IF EXISTS calculate_distance;

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