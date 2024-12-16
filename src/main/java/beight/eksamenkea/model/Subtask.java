package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.List;

public class Subtask extends ProjectTask {

    private final int taskID;
    private final int subtaskID;
    private final float estimatedHours;
    private final float spentHours;
    private final float kgCO2e;

    public static RowMapper<Subtask> ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new Subtask(rs.getInt("subtask.task_id"),
                    rs.getInt("subtask.subtask_id"),
                    rs.getString("subtask.title"),
                    rs.getFloat("subtask.estimated_hours"),
                    rs.getFloat("subtask.spent_hours"),
                    rs.getFloat("subtask.kg_CO2_e"));


    public Subtask(int taskID, int subtaskID, String title, float estimatedHours, float spentHours, float kgCO2e) {
        super(title);
        this.taskID = taskID;
        this.subtaskID = subtaskID;
        this.estimatedHours = estimatedHours;
        this.spentHours = spentHours;
        this.kgCO2e = kgCO2e;
    }

    public int getTaskID() {
        return taskID;
    }

    public int getSubtaskID() {
        return subtaskID;
    }

    @Override
    public float getEstimatedHours() {
        return estimatedHours;
    }

    @Override
    public float getSpentHours() {
        return spentHours;
    }

    @Override
    public float getKgCO2e() {
        return kgCO2e;
    }

    @Override
    public List<ProjectTask> getSubjects() {
        return null;
    }
}
