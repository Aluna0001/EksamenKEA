package beight.eksamenkea.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectTests {

    @Test
    void getTotalEstimatedHours() {
        Project project = new Project(1, "The project");
        Subproject subproject1 = new Subproject(1, 1, "Subproject1");
        Task task1 = new Task(1, 1, "Task1", LocalDateTime.now());
        Subproject subproject2 = new Subproject(1, 2, "Subproject2");
        Task task2 = new Task(2, 2, "Task2", LocalDateTime.now());
        task1.getSubtasks().add(new Subtask(1, 1, "Subtask1", 3.5f));
        task2.getSubtasks().add(new Subtask(2, 2, "Subtask2", 2.7f));
        subproject1.getTasks().add(task1);
        subproject2.getTasks().add(task2);
        project.getSubprojects().add(subproject1);
        project.getSubprojects().add(subproject2);
        assertEquals(6.2f, project.getTotalEstimatedHours());
    }
}
