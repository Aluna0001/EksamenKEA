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
    deadline DATETIME,
    subproject_id INT,
    FOREIGN KEY(subproject_id) REFERENCES subproject(subproject_id) ON DELETE CASCADE
);

CREATE TABLE subtask(
    subtask_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL,
    estimated_hours FLOAT NOT NULL,
    task_id INT,
    FOREIGN KEY(task_id) REFERENCES task(task_id) ON DELETE CASCADE
);

INSERT INTO project (title) VALUES ('The project');