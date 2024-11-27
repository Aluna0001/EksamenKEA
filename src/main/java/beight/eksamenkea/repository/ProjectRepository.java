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
}
