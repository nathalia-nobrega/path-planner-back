-- V3__Create_links_table.sql
CREATE TABLE links (
    id VARCHAR(36) NOT NULL DEFAULT (UUID()),
    title VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    trip_id CHAR(36),
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);
