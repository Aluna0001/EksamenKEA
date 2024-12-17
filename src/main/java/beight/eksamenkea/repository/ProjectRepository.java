package beight.eksamenkea.repository;

import beight.eksamenkea.model.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String readPassword(String username) throws EmptyResultDataAccessException {
        String sql = "SELECT encoded_password FROM user_profile WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, String.class, username);
    }

    public UserProfile readUserProfile(String username) {
        String sql = "SELECT * FROM user_profile WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, UserProfile.ROW_MAPPER, username);
    }

    public boolean createTask(int subprojectID, String title, LocalDateTime deadline) {
        String sql = "INSERT INTO task (subproject_id, title, deadline) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,subprojectID, title,deadline) > 0;
    }

    public List<Project> readAllProjects() {
        String sql = "SELECT * FROM project";
        return jdbcTemplate.query(sql, Project.ROW_MAPPER);
    }

    public Project readProject(int projectID) {
        String sql = """
                SELECT
                    *
                FROM
                    project
                LEFT JOIN
                    subproject ON project.project_id = subproject.project_id
                LEFT JOIN
                    task ON subproject.subproject_id = task.subproject_id
                LEFT JOIN
                    subtask ON task.task_id = subtask.task_id
                WHERE
                    project.project_id = ?
                ORDER BY
                    subproject.subproject_id, task.task_id""";
        ResultSetExtractor<Project> resultSetExtractor = (ResultSet rs) -> {
            Project project = null;
            Subproject subproject = null;
            Task task = null;
            while (rs.next()) {
                if (project == null) {
                    project = Project.ROW_MAPPER.mapRow(rs, 1);
                }
                int subprojectID = rs.getInt("subproject.subproject_id");
                if (subprojectID == 0) continue;
                if (subproject == null || subproject.getSubprojectID() != subprojectID) {
                    subproject = Subproject.ROW_MAPPER.mapRow(rs, 1);
                    project.getSubprojects().add(subproject);
                }
                int taskID = rs.getInt("task.task_id");
                if (taskID == 0) continue;
                if (task == null || task.getTaskID() != taskID) {
                    task = Task.ROW_MAPPER.mapRow(rs, 1);
                    subproject.getTasks().add(task);
                }
                if (rs.getInt("subtask.subtask_id") != 0) {
                    task.getSubtasks().add(Subtask.ROW_MAPPER.mapRow(rs, 1));
                }
            }
            return project;
        };
        return jdbcTemplate.query(sql, resultSetExtractor, projectID);
    }

    public Subproject readSubproject(int subprojectID) {
        String sql = """
                SELECT
                    *
                FROM
                    subproject
                LEFT JOIN
                    task ON subproject.subproject_id = task.subproject_id
                LEFT JOIN
                    subtask ON task.task_id = subtask.task_id
                WHERE
                    subproject.subproject_id = ?
                ORDER BY
                    task.task_id""";
        ResultSetExtractor<Subproject> resultSetExtractor = (ResultSet rs) -> {
            Subproject subproject = null;
            Task task = null;
            while (rs.next()) {
                if (subproject == null) {
                    subproject = Subproject.ROW_MAPPER.mapRow(rs, 1);
                }
                int taskID = rs.getInt("task.task_id");
                if (taskID == 0) continue;
                if (task == null || task.getTaskID() != taskID) {
                    task = Task.ROW_MAPPER.mapRow(rs, 1);
                    subproject.getTasks().add(task);
                }
                if (rs.getInt("subtask.subtask_id") != 0) {
                    task.getSubtasks().add(Subtask.ROW_MAPPER.mapRow(rs, 1));
                }
            }
            return subproject;
        };
        return jdbcTemplate.query(sql, resultSetExtractor, subprojectID);
    }
    public boolean createProject(String title) {
        String sql = "INSERT INTO project (title) VALUES (?)";
        return jdbcTemplate.update(sql, title) > 0;
    }

    public boolean createSubproject(int projectID, String title) {
        String sql = "INSERT INTO subproject (project_id, title) VALUES (?, ?)";
        return jdbcTemplate.update(sql, projectID, title) > 0;
    }

    public boolean createSubtask(int taskID, String title, float estimatedHours) {
        String sql = "INSERT INTO subtask (task_id, title, estimated_hours, spent_hours, kg_CO2_e) VALUES (?, ?, ?, 0, 0)";
        return jdbcTemplate.update(sql, taskID, title, estimatedHours) > 0;
    }

    public Task readTask(int task_id) {
        String sql = """
                SELECT
                    *
                FROM
                    task
                LEFT JOIN
                    subtask ON task.task_id = subtask.task_id
                WHERE
                    task.task_id = ?""";
        ResultSetExtractor<Task> resultSetExtractor = (ResultSet rs) -> {
            Task task = null;
            while (rs.next()) {
                if (task == null) {
                    task = Task.ROW_MAPPER.mapRow(rs, 1);
                }
                if (rs.getInt("subtask.subtask_id") != 0) {
                    task.getSubtasks().add(Subtask.ROW_MAPPER.mapRow(rs, 1));
                }
            }
            return task;
        };
        return jdbcTemplate.query(sql, resultSetExtractor, task_id);
    }

    public String readTitle(String type, int id) throws EmptyResultDataAccessException {
        if (!List.of("project", "subproject", "task", "subtask").contains(type)) return null;
        String sql = "SELECT title FROM " + type + " WHERE " + type + "_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public boolean updateTitle(String type, int id, String title) {
        if (!List.of("project", "subproject", "task", "subtask").contains(type)) return false;
        String sql = "UPDATE " + type + " SET title = ? WHERE " + type + "_id = ?";
        return jdbcTemplate.update(sql, title, id) > 0;
    }

    public boolean delete(String type, int id) {
        if (!List.of("project", "subproject", "task", "subtask").contains(type)) return false;
        String sql = "DELETE FROM " + type + " WHERE " + type + "_id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public int readSuperID(int id, String type, String superType) {
        String sql = "SELECT " + superType + "_id FROM " + type + " WHERE " + type + "_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id);
    }

    public Subtask readSubtask(int subtaskID) {
        String sql = "SELECT * FROM subtask WHERE subtask_id = ?";
        return jdbcTemplate.queryForObject(sql,Subtask.ROW_MAPPER,subtaskID);
    }

    public boolean updateDeadline(int taskID, LocalDateTime deadline) {
        String sql = "UPDATE task SET deadline = ? WHERE task_id = ?";
        return jdbcTemplate.update(sql, deadline, taskID) > 0;
    }

    public boolean addEstimatedHours(int subtaskID, float estimatedHours) {
        String sql = "UPDATE subtask SET estimated_hours = estimated_hours + ? WHERE subtask_id = ?";
        return jdbcTemplate.update(sql, estimatedHours, subtaskID) > 0;
    }

    public boolean updateEstimatedHours(int subtaskID, float estimatedHours) {
        String sql = "UPDATE subtask SET estimated_hours = ? WHERE subtask_id= ?";
        return jdbcTemplate.update(sql, estimatedHours, subtaskID) > 0;
    }

    public boolean toggleDarkMode(String username, boolean darkMode){
        String sql = "UPDATE user_profile SET darkmode = ? WHERE username = ?";
        return jdbcTemplate.update(sql,darkMode,username) > 0;
    }

    public boolean addSpentHours(int subtaskID, float spentHours) {
        String sql = "UPDATE subtask SET spent_hours = spent_hours + ? WHERE subtask_id = ?";
        return jdbcTemplate.update(sql, spentHours, subtaskID) > 0;
    }

    public boolean updateSpentHours(int subtaskID, float spentHours) {
        String sql = "UPDATE subtask SET spent_hours = ? WHERE subtask_id= ?";
        int rowsAffected = jdbcTemplate.update(sql, spentHours, subtaskID);
        return rowsAffected > 0;
    }

    public boolean addCO2e(int subtaskID, float CO2e) {
        String sql = "UPDATE subtask SET kg_CO2_e = kg_CO2_e + ? WHERE subtask_id = ?";
        return jdbcTemplate.update(sql, CO2e, subtaskID) > 0;
    }

    public boolean updateCO2e(int subtaskID, float co2e) {
        String sql = "UPDATE subtask SET kg_CO2_e = ? WHERE subtask_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, co2e, subtaskID);
        return rowsAffected > 0;
    }

}