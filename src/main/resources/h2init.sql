CREATE TABLE project(
    project_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL,
);

CREATE TABLE sub_project(
    subproject_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL,
    FOREIGN KEY(project_id) REFERENCES project(project_id) ON DELETE CASCADE
);

CREATE TABLE task(
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL,
    deadline DATETIME,
    FOREIGN KEY(subproject_id) REFERENCES sub_project(subproject_id) ON DELETE CASCADE
);

CREATE TABLE sub_task(
    subtask_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL,
    estimated_hours FLOAT NOT NULL,
    FOREIGN KEY(task_id) REFERENCES task(task_id) ON DELETE CASCADE
);
