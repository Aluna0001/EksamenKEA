package beight.eksamenkea.controller;

import beight.eksamenkea.model.Project;
import beight.eksamenkea.model.Subproject;
import beight.eksamenkea.model.Task;
import beight.eksamenkea.model.Subtask;
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
import java.time.LocalDateTime;

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
    private Subproject subproject;
    private Subtask subtask;

    @BeforeEach
    void setUp() {
        project = new Project(1, "The project");
        subproject = new Subproject(1, 1, "The subproject");
        subtask = new Subtask(1, 1, "The subtask", 1.25f);
    }

    @Test
    void viewFrontpage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/1"));
    }

    @Test
    void viewProject() throws Exception {
        when(projectService.getProject(1)).thenReturn(project);
        mockMvc.perform(get("/project/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("project"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attribute("project", project));
    }

    @Test
    void viewSubproject() throws Exception {
        when(projectService.getSubproject(1)).thenReturn(subproject);
        mockMvc.perform(get("/subproject/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("subproject"))
                .andExpect(model().attributeExists("subproject"))
                .andExpect(model().attribute("subproject", subproject));
    }

    @Test
    void createSubproject() throws Exception {
        mockMvc.perform(get("/project/1/create-subproject"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-subproject"));
    }

    @Test
    void saveNewSubprojectExpectFalse() throws Exception {
        when(projectService.createSubproject(1, "test")).thenReturn(false);
        mockMvc.perform(post("/subproject-created")
                        .param("id", "1")
                        .param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/1/create-subproject"));
    }

    @Test
    void saveNewSubprojectExpectTrue() throws Exception {
        when(projectService.createSubproject(1, "test")).thenReturn(true);
        mockMvc.perform(post("/subproject-created")
                        .param("id", "1")
                        .param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void createTask() throws Exception {
        mockMvc.perform(get("/subproject/1/create-task"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_task"));
    }

    @Test
    void saveNewTaskExpectFalse() throws Exception {
        when(projectService.createTask(1,"test", LocalDateTime.now())).thenReturn(false);
        when(projectService.getSubproject(1)).thenReturn(subproject);
        mockMvc.perform(post("/task-created")
                        .param("id", "1")
                        .param("title", "Test")
                        .param("deadline", "2024-12-18T08:30"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subproject/1/create-task"));
    }

    @Test
    void saveNewTaskExpectTrue() throws Exception {
        LocalDateTime deadline = LocalDateTime.now();
        when(projectService.createTask(1,"test", deadline)).thenReturn(true);
        when(projectService.getSubproject(1)).thenReturn(subproject);
        mockMvc.perform(post("/task-created")
                        .param("id", "1")
                        .param("title", "test")
                        .param("deadline", deadline.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subproject/1"));
    }

    @Test
    void createSubTask() throws Exception {
        mockMvc.perform(get("/task/1/create-subtask"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_subtask"));
    }

    @Test
    void saveNewSubTaskExpectFalse() throws Exception {
        when(projectService.createSubTask(1, "The sub task", 1, 1))
                .thenReturn(false);
        mockMvc.perform(post("/subtask-created")
                        .param("id", "1")
                        .param("title", "The sub task")
                        .param("estimated_time_hours", "1")
                        .param("estimated_time_minutes", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1/create-subtask"));
    }


    @Test
    void saveNewSubTaskExpectTrue() throws Exception {
        when(projectService.createSubTask(1, "The sub task", 1, 1))
                .thenReturn(true);
        mockMvc.perform(post("/subtask-created")
                        .param("id", "1")
                        .param("title", "The sub task")
                        .param("estimated_time_hours", "1")
                        .param("estimated_time_minutes", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1"));
    }

    @Test
    void readTask() throws Exception {
        when(projectService.getTask(1)).thenReturn(new Task(1,1,"task", LocalDateTime.now()));

        mockMvc.perform(get("/task/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("task"))
                .andExpect(model().attributeExists("task"));
    }

    @Test
    void viewSubtask() throws Exception {
        when(projectService.getSubtask(1)).thenReturn(subtask);
        mockMvc.perform(get("/subtask/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("subtask"))
                .andExpect(model().attributeExists("subtask"))
                .andExpect(model().attribute("subtask", subtask));
    }
    @Test
    void changeTitle() throws Exception {
        mockMvc.perform(get("/project/1/change-title"))
                .andExpect(status().isOk())
                .andExpect(view().name("update_title"));
    }

    @Test
    void saveTitleExpectTrue() throws Exception {
        when(projectService.updateTitle("project", 1, "test")).thenReturn(true);
        mockMvc.perform(post("/title-changed")
                        .param("type", "project")
                        .param("id", "1")
                        .param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/1"));
    }

    @Test
    void saveTitleExpectFalse() throws Exception {
        when(projectService.updateTitle("project", 1, "test")).thenReturn(false);
        mockMvc.perform(post("/title-changed")
                        .param("type", "project")
                        .param("id", "1")
                        .param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/1/change-title"));
    }
    @Test
    void updateTask() throws Exception {
        when(projectService.getTask(1)).thenReturn(new Task(1,1,"title",LocalDateTime.now()));

        mockMvc.perform(get("/subproject/1/update-task/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("update_task"))
                .andExpect(model().attributeExists("task"));
    }

    @Test
    void taskUpdated() throws Exception {
        when(projectService.updateTask(1, "title", LocalDateTime.now())).thenReturn(true);

        mockMvc.perform(post("/subproject/1/update-task/1")
                        .param("title","test")
                        .param("deadline","2024-12-18T08:30"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subproject/1/update-task/1"));
    }

    @Test
    void updateSubTask() throws Exception {
        when(projectService.getSubtask(1)).thenReturn(new Subtask(1,1,"test",3));

        mockMvc.perform(get("/task/1/update-subtask/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("update_subtask"))
                .andExpect(model().attributeExists("subtask"));
    }

    @Test
    void subTaskUpdated() throws Exception {
        when(projectService.updateSubTask(1,"test",3)).thenReturn(true);

        mockMvc.perform(post("/task/1/update-subtask/1")
                .param("title","test")
                .param("estimatedHours","3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1/update-subtask/1"));
    }

    @Test
    void deleteTask() throws Exception {
        when(projectService.deleteTask(1,"confirm")).thenReturn(true);

        mockMvc.perform(post("/subproject/1/delete-task/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/update-task"));
    }

    @Test
    void deleteSubTask() throws Exception {
        when(projectService.deleteSubTask(1,"confirm")).thenReturn(true);

        mockMvc.perform(post("/task/1/delete-subtask/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/update-subtask"));
    }

}

