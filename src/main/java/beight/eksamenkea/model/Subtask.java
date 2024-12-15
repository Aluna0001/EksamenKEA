package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public class Subtask {

    private final int taskID;
    private final int subtaskID;
    private final String title;
    private final float estimatedHours;
    private float spentHours;
    private float CO2e;
    private int percentageDone;

    public static RowMapper<Subtask> ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new Subtask(rs.getInt("subtask.task_id"),
                    rs.getInt("subtask.subtask_id"),
                    rs.getString("subtask.title"),
                    rs.getFloat("subtask.estimated_hours"),
                    rs.getFloat("subtask.co2e"),
                    rs.getFloat("subtask.spent_hours"));


    public Subtask(int taskID, int subtaskID, String title, float estimatedHours, float CO2e, float spentHours) {
        this.taskID = taskID;
        this.subtaskID = subtaskID;
        this.title = title;
        this.estimatedHours = estimatedHours;
        this.spentHours = spentHours;
        this.percentageDone = 0;
        this.CO2e = CO2e;
    }

    // Beregning af percentageDone
    public int calculatePercentageDone() {
        if (estimatedHours == 0) {
            return 0; // For at undgå division med 0
        }
        int percentage = (int) ((spentHours / estimatedHours) * 100);
        return Math.round(percentage);
    }

    public int getPercentageDone() {
        return calculatePercentageDone();

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

    public float getSpentHours() {
        return spentHours;
    }

    public float getCO2e() {
        return CO2e;
    }

}
