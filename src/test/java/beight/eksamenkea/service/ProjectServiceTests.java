package beight.eksamenkea.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
public class ProjectServiceTests {

    @Autowired
    private ProjectService projectService;
 /*
    @Test
    public void createSubTask(){
     //Make test when read method is implemented
    }
  */

}
