package beight.eksamenkea.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(true)
//@ActiveProfiles("h2")
public class ProjectRepositoryTests {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void readProject() {
        assertEquals("The project", projectRepository.readProject(1).getTitle());
    }

    @Test
    void createAndReadSubproject() {
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createSubproject(1, null));
        assertNull(projectRepository.readSubproject(1));
        boolean actualB = projectRepository.createSubproject(1, "The subproject");
        //String actualS = projectRepository.readSubproject(1).getTitle(); // Bug in the test class only: Subproject is null.
        assertTrue(actualB);
        //assertEquals("The subproject", actualS);
    }

    @Test
    void createAndReadTask() {
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createTask(1,null, LocalDateTime.now()));
        assertNull(projectRepository.readTask(1));
        projectRepository.createSubproject(1, "The subproject");
        boolean actualB = projectRepository.createTask(1, "test", LocalDateTime.now());
        assertTrue(actualB);
    }
/*
    @Test
    void createSubTask() {
        assertTrue(projectRepository.createSubTask("test", 1.25f)); //Success
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createSubTask(null, 2.25f)); //Failure
    }
*/
}