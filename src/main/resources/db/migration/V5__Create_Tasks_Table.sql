CREATE TABLE IF NOT EXISTS tasks (
                       task_id INT AUTO_INCREMENT PRIMARY KEY,
                       group_id INT NOT NULL,
                       task_name VARCHAR(255) NOT NULL,
                       assigned_to INT,
                       status ENUM('TODO', 'IN_PROGRESS', 'DONE') NOT NULL DEFAULT 'TODO',
                       due_date DATE,
                       FOREIGN KEY (group_id) REFERENCES `groups`(group_id) ON DELETE CASCADE,
                       FOREIGN KEY (assigned_to) REFERENCES users(user_id) ON DELETE SET NULL
);