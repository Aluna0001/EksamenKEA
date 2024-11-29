package beight.eksamenkea.repository;

import beight.eksamenkea.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository {

    private JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Project readProject() {
        String sql = "select * from project";
        return jdbcTemplate.queryForObject(sql, Project.ROW_MAPPER);
    }

    public boolean createSubTask(String title, float estimatedHours) {
        String sql = "INSERT INTO subtask (title, estimated_hours) VALUES (?, ?)";
        //jdbcTemplate.update returns int
        //to return a boolean, > 0 is added at the end, which also makes sure that a change has been made
        return jdbcTemplate.update(sql, title, estimatedHours) > 0;
    }
}
