-- V2__Create_activities_table.sql
CREATE TABLE activities (
    id VARCHAR(36) NOT NULL DEFAULT (UUID()),
    title VARCHAR(255) NOT NULL,
    occurs_at TIMESTAMP NOT NULL,
    trip_id CHAR(36),
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);
