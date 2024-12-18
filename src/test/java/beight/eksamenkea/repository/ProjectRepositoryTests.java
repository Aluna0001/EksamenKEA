package beight.eksamenkea.repository;

import beight.eksamenkea.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("h2")
public class ProjectRepositoryTests {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void readAllProjects() {
        assertEquals(1, projectRepository.readAllProjects().size());
    }

    @Test
    void readProject() {
        assertNull(projectRepository.readProject(2));
        assertEquals("The project", projectRepository.readProject(1).getTitle());
    }

    @Test
    void readSubproject() {
        assertNull(projectRepository.readSubproject(2));
        assertEquals("The subproject", projectRepository.readSubproject(1).getTitle());
    }

    @Test
    void readTask() {
        assertNull(projectRepository.readTask(2));
        assertEquals("The task", projectRepository.readTask(1).getTitle());
    }

    @Test
    void readSubtask() {
        assertThrows(EmptyResultDataAccessException.class, () -> projectRepository.readSubtask(2));
        assertEquals("The subtask", projectRepository.readSubtask(1).getTitle());
    }

    @Test
    void createProject() {
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createProject(null));
        assertTrue(projectRepository.createProject("test"));
    }

    @Test
    void createSubproject() {
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createSubproject(1, null));
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createSubproject(2, "test"));
        assertTrue(projectRepository.createSubproject(1, "test"));
    }

    @Test
    void createTask() {
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createTask(1, null, LocalDateTime.now()));
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createTask(2, "test", LocalDateTime.now()));
        assertTrue(projectRepository.createTask(1, "test", LocalDateTime.now()));
    }

    @Test
    void createSubtask() {
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createSubtask(1, null, 1f));
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createSubtask(2, "test", 1f));
        assertTrue(projectRepository.createSubtask(1, "test", 1f));
    }

    @Test
    void readTitle() {
        assertNull(projectRepository.readTitle("wrongHere", 1));
        assertThrows(EmptyResultDataAccessException.class, () -> projectRepository.readTitle("project", 2));
        assertEquals("The project", projectRepository.readTitle("project", 1));
        assertEquals("The subproject", projectRepository.readTitle("subproject", 1));
    }

    @Test
    void updateTitle() {
        assertFalse(projectRepository.updateTitle("wrongHere", 1, "test"));
        assertFalse(projectRepository.updateTitle("project", 2, "test"));
        assertTrue(projectRepository.updateTitle("project", 1, "test"));
        assertTrue(projectRepository.updateTitle("subproject", 1, "test"));
    }

    @Test
    void delete() {
        assertFalse(projectRepository.delete("wrongHere", 1));

        assertFalse(projectRepository.delete("project", 2));
        assertFalse(projectRepository.delete("subproject", 2));
        assertFalse(projectRepository.delete("task", 2));
        assertFalse(projectRepository.delete("subtask", 2));

        assertTrue(projectRepository.delete("subtask", 1));
        assertTrue(projectRepository.delete("task", 1));
        assertTrue(projectRepository.delete("subproject", 1));
        assertTrue(projectRepository.delete("project", 1));
    }

    @Test
    void toggleDarkMode() {
        assertTrue(projectRepository.toggleDarkMode("admin", true));
        assertTrue(projectRepository.toggleDarkMode("admin", false));
    }

    @Test
    void addSpentHours() {
        assertTrue(projectRepository.addSpentHours(1, 2));
    }

    @Test
    void updateSpentHours() {
        assertTrue(projectRepository.updateSpentHours(1, 2));
    }
    @Test
    void addCO2e() {
        assertTrue(projectRepository.addCO2e(1, 2));
    }

    @Test
    void updateCO2e() {
        assertTrue(projectRepository.updateCO2e(1, 2));
    }


}