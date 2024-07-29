-- V4__Create_users_table.sql
CREATE TABLE users (
    id VARCHAR(36) NOT NULL DEFAULT (UUID()),
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
