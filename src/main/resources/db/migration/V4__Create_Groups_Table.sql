CREATE TABLE IF NOT EXISTS `groups` (
                          group_id INT AUTO_INCREMENT PRIMARY KEY,
                          project_id INT NOT NULL,
                          group_name VARCHAR(255) NOT NULL,
                          FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE
);