package beight.eksamenkea.repository;
import beight.eksamenkea.model.Project;
import beight.eksamenkea.model.Subproject;
import beight.eksamenkea.model.Subtask;
import beight.eksamenkea.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;

@Repository
public class ProjectRepository {

    private JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean createTask(String title, LocalDate deadline) {
        String sql = "INSERT INTO task (title, deadline) VALUES (?, ?)";
        return jdbcTemplate.update(sql,title,deadline) > 0;
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

    public boolean createSubproject(int projectID, String title) {
        String sql = "INSERT INTO subproject (project_id, title) VALUES (?, ?)";
        return jdbcTemplate.update(sql, projectID, title) > 0;
    }

    public boolean createSubTask(String title, float estimatedHours) {
        String sql = "INSERT INTO subtask (title, estimated_hours) VALUES (?, ?)";
        //jdbcTemplate.update returns int
        //to return a boolean, > 0 is added at the end, which also makes sure that a change has been made
        return jdbcTemplate.update(sql, title, estimatedHours) > 0;
    }
}