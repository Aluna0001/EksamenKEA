package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends ProjectTask {

    private final int subprojectID;
    private final int taskID;
    private final LocalDateTime deadline;
    private final List<Subtask> subtasks;

    public static RowMapper<Task> ROW_MAPPER =(ResultSet rs, int rowNum) ->
        new Task(rs.getInt("task.subproject_id"),
                rs.getInt("task.task_id"),
                rs.getString("task.title"),
                rs.getTimestamp("task.deadline").toLocalDateTime());

    public Task(int subprojectID, int taskID, String title, LocalDateTime deadline) {
        super(title);
        this.subprojectID = subprojectID;
        this.taskID = taskID;
        this.deadline = deadline;
        this.subtasks = new ArrayList<>();
    }

    public String date() {
        String month = deadline.getMonth().toString();
        String date = deadline.getDayOfMonth() + " " + month.charAt(0) + month.substring(1).toLowerCase();
        String year = deadline.getYear() == LocalDateTime.now().getYear() ? "" : (" " + deadline.getYear());
        return date + year;
    }

    public String time() {
        int hour = deadline.getHour();
        return hour + ":" + deadline.getMinute() + (hour > 0 && hour < 13 ? " a.m." : "");
    }

    public boolean passed() {
        return deadline.isBefore(LocalDateTime.now());
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public int getTaskID() {
        return taskID;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public List<ProjectTask> getSubjects() {
        return new ArrayList<>(subtasks);
    }
}
