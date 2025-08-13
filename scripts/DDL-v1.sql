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

-- ===========================================
-- Table: label
-- Represents labels created by a user
-- ===========================================
CREATE TABLE label(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    system_user_id BIGINT NOT NULL REFERENCES system_user(id),
    creation_date TIMESTAMP NOT NULL,
    last_modification_date TIMESTAMP NOT NULL
);

-- ===========================================
-- Table: note
-- Stores notes created by users
-- ===========================================
CREATE TABLE note(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    system_user_id BIGINT NOT NULL REFERENCES system_user(id),
    label_id BIGINT REFERENCES label(id) REFERENCES note(id),
    is_archived BOOLEAN NOT NULL DEFAULT FALSE,
    creation_date TIMESTAMP NOT NULL,
    last_modification_date TIMESTAMP NOT NULL
);

-- ===========================================
-- Table: label_note
-- Allows a note to have multiple labels and vice versa
-- ===========================================
CREATE TABLE label_note(
    id_label BIGINT NOT NULL REFERENCES label(id),
    id_note BIGINT NOT NULL REFERENCES note(id),
    creation_date TIMESTAMP NOT NULL,
    last_modification_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id_label, id_note)
);
