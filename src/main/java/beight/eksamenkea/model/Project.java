package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public class Project {

    private final String title;

    public static RowMapper<Project> ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new Project(rs.getString("title"));

    public Project(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
