CREATE TABLE IF NOT EXISTS project_users (
                               project_user_id INT AUTO_INCREMENT PRIMARY KEY,
                               project_id INT NOT NULL,
                               user_id INT NOT NULL,
                               FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE,
                               FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                               UNIQUE(project_id, user_id)
);