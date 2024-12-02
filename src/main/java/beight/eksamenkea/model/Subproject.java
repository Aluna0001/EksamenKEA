package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

public class Subproject {

    private final int projectID;
    private final int subprojectID;
    private final String title;
    private final List<Task> tasks;

    public static RowMapper<Subproject> ROW_MAPPER = (rs, rowNum) ->
            new Subproject(rs.getInt("subproject.project_id"),
                    rs.getInt("subproject.subproject_id"),
                    rs.getString("subproject.title"));

    public Subproject(int projectID, int subprojectID, String title) {
        this.projectID = projectID;
        this.subprojectID = subprojectID;
        this.title = title;
        this.tasks = new ArrayList<>();
    }

    public float getTotalEstimatedHours() {
        float sum = 0;
        for (Task task : tasks) {
            sum += task.getTotalEstimatedHours();
        }
        return sum;
    }

    public int getProjectID() {
        return projectID;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public String getTitle() {
        return title;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
