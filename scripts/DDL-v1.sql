-- ===========================================
-- Table: system_user
-- Stores the basic data of each user
-- ===========================================
CREATE TABLE system_user (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    full_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    last_modification_date TIMESTAMP NOT NULL
);
