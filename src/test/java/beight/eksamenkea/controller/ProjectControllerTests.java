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

import java.time.LocalDate;

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
    void createTask() throws Exception {
        mockMvc.perform(get("/create-task"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_task"));
    }

    @Test
    void saveNewTaskExpectFalse() throws Exception {
        when(projectService.createTask("test", LocalDate.parse("2024-12-18"))).thenReturn(false);
        mockMvc.perform(post("/task-created")
                        .param("title", "Test")
                        .param("deadline", "2024-12-18"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/create-task"));
    }

    @Test
    void saveNewTaskExpectTrue() throws Exception {
        when(projectService.createTask("test", LocalDate.parse("2024-12-18"))).thenReturn(true);
        mockMvc.perform(post("/task-created")
                        .param("title", "Test")
                        .param("deadline", "2024-12-18"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/create-task")); // ret tilbage til redirect senere
    }

    @Test
    void createSubTask() throws Exception {
        mockMvc.perform(get("/create-sub-task"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_sub_task"));
    }

    @Test
    void saveNewSubTaskExpectFalse() throws Exception {
        when(projectService.createSubTask("The sub task", 1, 1))
                .thenReturn(false);
        mockMvc.perform(post("/sub-task-created")
                        .param("title", "The sub task")
                        .param("estimated_time_hours", "1")
                        .param("estimated_time_minutes", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/create-sub-task"));
    }


    @Test
    void saveNewSubTaskExpectTrue() throws Exception {
        when(projectService.createSubTask("The sub task", 1, 1))
                .thenReturn(true);
        mockMvc.perform(post("/sub-task-created")
                        .param("title", "The sub task")
                        .param("estimated_time_hours", "1")
                        .param("estimated_time_minutes", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/")); //Figure out where to redirect
    }
}

