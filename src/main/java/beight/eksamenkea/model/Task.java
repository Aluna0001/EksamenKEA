package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.Date;

public class Task {

    private String title;
    private Date deadline;



    public static RowMapper<Task> ROW_MAPPER =(ResultSet rs, int rowNum) ->
        new Task(rs.getString("title"),
            rs.getDate("deadline"));


    public Task(String title, Date deadline) {
        this.title = title;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public Date getDeadline() {
        return deadline;
    }
}
