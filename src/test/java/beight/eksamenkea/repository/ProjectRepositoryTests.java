package beight.eksamenkea.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@ActiveProfiles("h2")
public class ProjectRepositoryTests {

    @Autowired
    private ProjectRepository projectRepository;

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
    void createSubproject() {
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createSubproject(1, null));
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createSubproject(2, "test"));
        assertTrue(projectRepository.createSubproject(1, "test"));
    }

    @Test
    void createTask() {
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createTask(1,null, LocalDateTime.now()));
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createTask(2, "test", LocalDateTime.now()));
        assertTrue(projectRepository.createTask(1, "test", LocalDateTime.now()));
    }
/*
    @Test
    void createSubTask() {
        assertTrue(projectRepository.createSubTask("test", 1.25f)); //Success
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createSubTask(null, 2.25f)); //Failure
    }
*/
    @Test
    void readTitle() {
        assertNull(projectRepository.readTitle("wrongHere", 1));
        assertThrows(EmptyResultDataAccessException.class, () -> projectRepository.readTitle("project", 2));
        assertEquals("The project", projectRepository.readTitle("project", 1));
    }

    @Test
    void updateTitle() {
        assertFalse(projectRepository.updateTitle("wrongHere", 1, "test"));
        assertFalse(projectRepository.updateTitle("project", 2, "test"));
        assertTrue(projectRepository.updateTitle("project", 1, "test"));
    }
}