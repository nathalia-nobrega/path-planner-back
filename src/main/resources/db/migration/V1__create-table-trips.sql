-- V1__Create_trips_table.sql
CREATE TABLE trips (
    id VARCHAR(36) NOT NULL DEFAULT(UUID()),
    destination VARCHAR(255) NOT NULL,
    starts_at TIMESTAMP NOT NULL,
    ends_at TIMESTAMP NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    owner_name VARCHAR(255) NOT NULL,
    owner_email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
