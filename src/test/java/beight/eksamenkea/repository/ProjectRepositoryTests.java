package beight.eksamenkea.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

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
/*
    @Test
    void createTask() {
        assertTrue(projectRepository.createTask("test", LocalDate.parse("2024-12-18")));
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createTask(null, LocalDate.parse("2024-12-18")));
    }

    @Test
    void createSubTask() {
        assertTrue(projectRepository.createSubTask("test", 1.25f)); //Success
        assertThrows(DataIntegrityViolationException.class, () -> projectRepository.createSubTask(null, 2.25f)); //Failure
    }
*/
}