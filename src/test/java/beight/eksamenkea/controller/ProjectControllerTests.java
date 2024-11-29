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

    @Test
    void createSubProject() throws Exception {
        mockMvc.perform(get("/create-subproject"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-subproject"));
    }

    @Test
    void saveNewSubProjectExpectFalse() throws Exception {
        when(projectService.createSubProject("test")).thenReturn(false);
        mockMvc.perform(post("/save-subproject")
                        .param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/create-subproject"));
    }

    @Test
    void saveNewSubProjectExpectTrue() throws Exception {
        when(projectService.createSubProject("test")).thenReturn(true);
        mockMvc.perform(post("/save-subproject")
                        .param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
