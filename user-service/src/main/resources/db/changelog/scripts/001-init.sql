CREATE TABLE users
(
    user_id   BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    email     VARCHAR(255),
    age       INTEGER,
    created_at TIMESTAMP
);