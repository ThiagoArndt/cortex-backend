CREATE TABLE IF NOT EXISTS comments (
                          comment_id INT PRIMARY KEY AUTO_INCREMENT,
                          task_id INT NOT NULL,
                          user_id INT NOT NULL,
                          content TEXT NOT NULL,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (task_id) REFERENCES tasks(task_id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES users(user_id)
);