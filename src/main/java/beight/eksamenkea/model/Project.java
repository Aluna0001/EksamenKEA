package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Project {

    private final int projectID;
    private final String title;
    private final List<Subproject> subprojects;

    public static RowMapper<Project> ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new Project(rs.getInt("project.project_id"),
                    rs.getString("project.title"));

    public Project(int projectID, String title) {
        this.projectID = projectID;
        this.title = title;
        subprojects = new ArrayList<>();
    }

    public float getTotalEstimatedHours() {
        float sum = 0;
        for (Subproject subproject : subprojects) {
            sum += subproject.getTotalEstimatedHours();
        }
        return sum;
    }

    public float getTotalSpentHours(){
        float sum = 0;
        for (Subproject subproject : subprojects) {
            sum += subproject.getTotalSpentHours();
        }
        return sum;
    }

    public float getTotalCO2E(){
        float sum = 0;
        for (Subproject subproject : subprojects) {
            sum += subproject.getTotalCO2E();
        }
        return sum;
    }

    public int getPercentageDone() {
        int sum = 0;
        for (Subproject subproject : subprojects) {
            sum += subproject.getPercentageDone();
        }
        return sum;
    }

    public int getProjectID() {
        return projectID;
    }

    public String getTitle() {
        return title;
    }

    public List<Subproject> getSubprojects() {
        return subprojects;
    }
}
