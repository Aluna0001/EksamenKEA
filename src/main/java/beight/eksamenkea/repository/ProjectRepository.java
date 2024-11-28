package beight.eksamenkea.repository;

import beight.eksamenkea.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class ProjectRepository {

    private JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean createTask(String title, Date deadline) {
        String sql = "INSERT INTO task (title, deadline) VALUES (?, ?) ";
        return jdbcTemplate.update(sql,title,deadline) > 0;
    }

    public Project readProject() {
        String sql = "select * from project";
        return jdbcTemplate.queryForObject(sql, Project.ROW_MAPPER);
    }
}
