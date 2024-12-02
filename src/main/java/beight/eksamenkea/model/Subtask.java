package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public class Subtask {

    private final int taskID;
    private final int subtaskID;
    private final String title;
    private final float estimatedHours;

    public static RowMapper<Subtask> ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new Subtask(rs.getInt("subtask.task_id"),
                    rs.getInt("subtask.subtask_id"),
                    rs.getString("subtask.title"),
                    rs.getFloat("subtask.estimated_hours"));

    public Subtask(int taskID, int subtaskID, String title, float estimatedHours) {
        this.taskID = taskID;
        this.subtaskID = subtaskID;
        this.title = title;
        this.estimatedHours = estimatedHours;
    }

    public int getTaskID() {
        return taskID;
    }

    public int getSubtaskID() {
        return subtaskID;
    }

    public String getTitle() {
        return title;
    }

    public float getEstimatedHours() {
        return estimatedHours;
    }
}
