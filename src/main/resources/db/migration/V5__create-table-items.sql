-- V5__Create_items_table.sql
CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    trip_id CHAR(36),
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);
