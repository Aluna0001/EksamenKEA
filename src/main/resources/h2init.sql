CREATE TABLE user_profile(
    user_profile_id INT PRIMARY KEY AUTO_INCREMENT,
    username CHAR(6) NOT NULL UNIQUE,
    encoded_password CHAR(60) NOT NULL,
    darkmode BOOLEAN NOT NULL
);

CREATE TABLE project(
    project_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL
);

CREATE TABLE subproject(
    subproject_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL,
    project_id INT NOT NULL,
    FOREIGN KEY(project_id) REFERENCES project(project_id) ON DELETE CASCADE
);

CREATE TABLE task(
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL,
    deadline DATETIME NOT NULL,
    subproject_id INT NOT NULL,
    FOREIGN KEY(subproject_id) REFERENCES subproject(subproject_id) ON DELETE CASCADE
);

CREATE TABLE subtask(
    subtask_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL,
    estimated_hours FLOAT NOT NULL,
    task_id INT NOT NULL,
    spent_hours FLOAT,
    co2e FLOAT,
    percentage_done INT,
    FOREIGN KEY(task_id) REFERENCES task(task_id) ON DELETE CASCADE
);

INSERT INTO user_profile (username, encoded_password, darkmode) VALUES ('jeha00', '$2a$10$q8XQmvEWn0.2Z51n.ZfqledPZXjAiI9iFfvXAppK0aSTVqcfKMgeK', true);
INSERT INTO project (title) VALUES ('The project');
INSERT INTO subproject (project_id, title) VALUES (1, 'The subproject');
INSERT INTO task (subproject_id, title, deadline) VALUES (1, 'The task', NOW());
INSERT INTO subtask (task_id, title, estimated_hours, spent_hours, co2e, percentage_done) VALUES (1, 'The subtask', 3,2,5,90);