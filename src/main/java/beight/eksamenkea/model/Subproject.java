package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

public class Subproject extends ProjectTask {

    private final int projectID;
    private final int subprojectID;
    private final List<Task> tasks;

    public static RowMapper<Subproject> ROW_MAPPER = (rs, rowNum) ->
            new Subproject(rs.getInt("subproject.project_id"),
                    rs.getInt("subproject.subproject_id"),
                    rs.getString("subproject.title"));

    public Subproject(int projectID, int subprojectID, String title) {
        super(title);
        this.projectID = projectID;
        this.subprojectID = subprojectID;
        this.tasks = new ArrayList<>();
    }

    public int getProjectID() {
        return projectID;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public List<ProjectTask> getSubjects() {
        return new ArrayList<>(tasks);
    }
}
