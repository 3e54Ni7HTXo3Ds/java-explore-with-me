DROP TABLE IF EXISTS stats CASCADE;

CREATE TABLE IF NOT EXISTS stats
(
    stats_id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    stats_timestamp TIMESTAMP WITH TIME ZONE,
    stats_ip        VARCHAR(50),
    stats_app       VARCHAR(100),
    stats_uri       VARCHAR(500)

);
