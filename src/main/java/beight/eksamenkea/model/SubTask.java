package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public class SubTask {
    private final String title;
    private final float estimatedHours;

    public static RowMapper<SubTask> ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new SubTask(rs.getString("title"),
                    rs.getFloat("estimatedHours"));

    public SubTask(String title, float estimatedHours) {
        this.title = title;
        this.estimatedHours = estimatedHours;
    }

    public String getTitle() {
        return title;
    }

    public float getEstimatedHours() {
        return estimatedHours;
    }
}
