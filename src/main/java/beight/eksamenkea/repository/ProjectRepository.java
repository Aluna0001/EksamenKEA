package beight.eksamenkea.repository;
import beight.eksamenkea.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
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

    public Project readProject() {
        String sql = "select * from project";
        return jdbcTemplate.queryForObject(sql, Project.ROW_MAPPER);
    }

    public boolean createSubProject(String title) {
        String sql = "insert into subproject (title) values (?)";
        return jdbcTemplate.update(sql, title) > 0;
    }

    public boolean createSubTask(String title, float estimatedHours) {
        String sql = "INSERT INTO subtask (title, estimated_hours) VALUES (?, ?)";
        //jdbcTemplate.update returns int
        //to return a boolean, > 0 is added at the end, which also makes sure that a change has been made
        return jdbcTemplate.update(sql, title, estimatedHours) > 0;
    }
}