package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private final int subprojectID;
    private final int taskID;
    private final String title;
    private final LocalDateTime deadline;
    private final List<Subtask> subtasks;

    public static RowMapper<Task> ROW_MAPPER =(ResultSet rs, int rowNum) ->
        new Task(rs.getInt("task.subproject_id"),
                rs.getInt("task.task_id"),
                rs.getString("task.title"),
                rs.getTimestamp("task.deadline").toLocalDateTime());

    public Task(int subprojectID, int taskID, String title, LocalDateTime deadline) {
        this.subprojectID = subprojectID;
        this.taskID = taskID;
        this.title = title;
        this.deadline = deadline;
        this.subtasks = new ArrayList<>();
    }

    public float getTotalEstimatedHours() {
        float sum = 0;
        for (Subtask subtask : subtasks) {
            sum += subtask.getEstimatedHours();
        }
        return sum;
    }

    public float getTotalSpentHours(){
        float sum = 0;
        for (Subtask subtask : subtasks) {
            sum += subtask.getSpentHours();
        }
        return sum;
    }

    public float getTotalCO2E(){
        float sum = 0;
        for (Subtask subtask : subtasks) {
            sum += subtask.getCO2e();
        }
        return sum;
    }

    public int getPercentageDone() {
        int sum = 0;
        for (Subtask subtask : subtasks) {
            sum += subtask.getPercentageDone();
        }
        return sum;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public int getTaskID() {
        return taskID;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }
}
