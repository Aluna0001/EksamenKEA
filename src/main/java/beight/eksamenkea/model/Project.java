package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Project extends ProjectTask {

    private final int projectID;
    private final List<Subproject> subprojects;

    public static RowMapper<Project> ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new Project(rs.getInt("project.project_id"),
                    rs.getString("project.title"));

    public Project(int projectID, String title) {
        super(title);
        this.projectID = projectID;
        subprojects = new ArrayList<>();
    }

    public int getProjectID() {
        return projectID;
    }

    public List<Subproject> getSubprojects() {
        return subprojects;
    }

    @Override
    public List<ProjectTask> getSubjects() {
        return new ArrayList<>(subprojects);
    }
}
