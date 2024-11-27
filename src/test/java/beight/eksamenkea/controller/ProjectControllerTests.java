package beight.eksamenkea.controller;

import beight.eksamenkea.model.Project;
import beight.eksamenkea.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project("The project");
    }

    @Test
    void viewFrontpage() throws Exception {
        when(projectService.getProject()).thenReturn(project);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("project"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attribute("project", project));
    }

}
