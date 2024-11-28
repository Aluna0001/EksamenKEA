package beight.eksamenkea.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback(true)
//@ActiveProfiles("h2")
public class ProjectRepositoryTests {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void readProject() {
        assertEquals("The project", projectRepository.readProject().getTitle());
    }
}