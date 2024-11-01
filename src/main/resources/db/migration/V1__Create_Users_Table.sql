CREATE TABLE IF NOT EXISTS users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       user_password VARCHAR(255) NOT NULL
);