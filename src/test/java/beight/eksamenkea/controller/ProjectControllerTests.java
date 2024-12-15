package beight.eksamenkea.controller;

import beight.eksamenkea.model.*;
import beight.eksamenkea.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private List<Project> projects;
    private Project project;
    private Subproject subproject;
    private Subtask subtask;
    @MockitoBean
    MockHttpSession session;

    @BeforeEach
    void setUp() {
        projects = new ArrayList<>();
        project = new Project(1, "The project");
        subproject = new Subproject(1, 1, "The subproject");
        subtask = new Subtask(1, 1, "The subtask", 1.25f, 5, 2);
        when(session.getAttribute("userProfile")).
                thenReturn(new UserProfile(1, "jeha00", true));

    }

    /*
        @Test
        void viewFrontpage() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/project/1"));
        }
    */
    @Test
    void viewAllProjects() throws Exception {
        mockMvc.perform(get("/projects")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("projects"))
                .andExpect(model().attributeExists("projects"))
                .andExpect(model().attribute("projects", projects));
    }

    @Test
    void viewProject() throws Exception {
        when(projectService.getProject(1)).thenReturn(project);
        mockMvc.perform(get("/project/1")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("project"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attribute("project", project));
    }

    @Test
    void viewSubproject() throws Exception {
        when(projectService.getSubproject(1)).thenReturn(subproject);
        mockMvc.perform(get("/subproject/1")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("subproject"))
                .andExpect(model().attributeExists("subproject"))
                .andExpect(model().attribute("subproject", subproject));
    }

    @Test
    void createProject() throws Exception {
        mockMvc.perform(get("/create-project")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("create_project"));
    }

    @Test
    void saveNewProjectExpectFalse() throws Exception {
        when(projectService.createProject("test")).thenReturn(false);
        mockMvc.perform(post("/project-created")
                        .session(session)
                        .param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/create-project"));
    }

    @Test
    void saveNewProjectExpectTrue() throws Exception {
        when(projectService.createProject("test")).thenReturn(true);
        mockMvc.perform(post("/project-created")
                        .session(session)
                        .param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

    @Test
    void createSubproject() throws Exception {
        mockMvc.perform(get("/project/1/create-subproject")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("create_subproject"));
    }

    @Test
    void saveNewSubprojectExpectFalse() throws Exception {
        when(projectService.createSubproject(1, "test")).thenReturn(false);
        mockMvc.perform(post("/subproject-created")
                        .session(session)
                        .param("id", "1")
                        .param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/1/create-subproject"));
    }

    @Test
    void saveNewSubprojectExpectTrue() throws Exception {
        when(projectService.createSubproject(1, "test")).thenReturn(true);
        mockMvc.perform(post("/subproject-created")
                        .session(session)
                        .param("id", "1")
                        .param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/1"));
    }

    @Test
    void createTask() throws Exception {
        mockMvc.perform(get("/subproject/1/create-task")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("create_task"));
    }

    @Test
    void saveNewTaskExpectFalse() throws Exception {
        when(projectService.createTask(1, "test", LocalDateTime.now())).thenReturn(false);
        when(projectService.getSubproject(1)).thenReturn(subproject);
        mockMvc.perform(post("/task-created")
                        .session(session)
                        .param("id", "1")
                        .param("title", "Test")
                        .param("deadline", "2024-12-18T08:30"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subproject/1/create-task"));
    }

    @Test
    void saveNewTaskExpectTrue() throws Exception {
        LocalDateTime deadline = LocalDateTime.now();
        when(projectService.createTask(1, "test", deadline)).thenReturn(true);
        when(projectService.getSubproject(1)).thenReturn(subproject);
        mockMvc.perform(post("/task-created")
                        .session(session)
                        .param("id", "1")
                        .param("title", "test")
                        .param("deadline", deadline.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subproject/1"));
    }

    @Test
    void createSubTask() throws Exception {
        mockMvc.perform(get("/task/1/create-subtask")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("create_subtask"));
    }

    @Test
    void saveNewSubTaskExpectFalse() throws Exception {
        when(projectService.createSubTask(1, "The sub task", 1, 5))
                .thenReturn(false);
        mockMvc.perform(post("/subtask-created")
                        .session(session)
                        .param("id", "1")
                        .param("title", "The sub task")
                        .param("estimated_time_hours", "1")
                        .param("estimated_time_minutes", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1/create-subtask"));
    }

    @Test
    void saveNewSubTaskExpectTrue() throws Exception {
        when(projectService.createSubTask(1, "The sub task", 1, 5))
                .thenReturn(true);
        mockMvc.perform(post("/subtask-created")
                        .session(session)
                        .param("id", "1")
                        .param("title", "The sub task")
                        .param("estimated_time_hours", "1")
                        .param("estimated_time_minutes", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1"));
    }

    @Test
    void readTask() throws Exception {
        when(projectService.getTask(1)).thenReturn(new Task(1, 1, "task", LocalDateTime.now()));
        mockMvc.perform(get("/task/1")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("task"))
                .andExpect(model().attributeExists("task"));
    }

    @Test
    void viewSubtask() throws Exception {
        when(projectService.getSubtask(1)).thenReturn(subtask);
        mockMvc.perform(get("/subtask/1")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("subtask"))
                .andExpect(model().attributeExists("subtask"))
                .andExpect(model().attribute("subtask", subtask));
    }

    @Test
    void changeTitleProject() throws Exception {
        when(projectService.getTitle("project", 1)).thenReturn("The project");
        mockMvc.perform(get("/project/1/change-title")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("update_title"))
                .andExpect(model().attribute("type", "project"))
                .andExpect(model().attribute("id", 1))
                .andExpect(model().attribute("title", "The project"));
    }


    @Test
    void saveTitleExpectTrue() throws Exception {
        when(projectService.updateTitle("project", 1, "test")).thenReturn(true);
        mockMvc.perform(post("/title-changed")
                        .session(session)
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
                        .session(session)
                        .param("type", "project")
                        .param("id", "1")
                        .param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/1/change-title"));
    }

    @Test
    void deleteProject() throws Exception {
        when(projectService.getTitle("project", 1)).thenReturn("The project");
        mockMvc.perform(get("/project/1/delete")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("delete"))
                .andExpect(model().attribute("url", "/projects"))
                .andExpect(model().attribute("type", "project"))
                .andExpect(model().attribute("id", 1))
                .andExpect(model().attribute("title", "The project"));
    }

    @Test
    void deleteOption() throws Exception {
        when(projectService.getTitle("subproject", 1)).thenReturn("The subproject");
        mockMvc.perform(get("/project/1/subproject/1/delete")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("delete"))
                .andExpect(model().attribute("url", "/project/1"))
                .andExpect(model().attribute("type", "subproject"))
                .andExpect(model().attribute("id", 1))
                .andExpect(model().attribute("title", "The subproject"));
    }

    @Test
    void deleteWithConfirmation() throws Exception {
        when(projectService.delete("subproject", 1, true)).thenReturn(true);
        mockMvc.perform(post("/deleted")
                        .session(session)
                        .param("url", "/project/1")
                        .param("type", "subproject")
                        .param("id", "1")
                        .param("confirm", "on"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/1"));
    }

    @Test
    void deleteWithoutConfirmation() throws Exception {
        when(projectService.delete("subproject", 1, false)).thenReturn(false);
        mockMvc.perform(post("/deleted")
                        .session(session)
                        .param("url", "/project/1")
                        .param("type", "subproject")
                        .param("id", "1")
                        .param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/1/subproject/1/delete"));
    }


    @Test
    void updateTask() throws Exception {
        when(projectService.getTask(1)).thenReturn(new Task(1, 1, "title", LocalDateTime.now()));
        mockMvc.perform(get("/subproject/1/update-task/1")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("update_task"))
                .andExpect(model().attributeExists("task"));
    }

    @Test
    void taskUpdated() throws Exception {
        when(projectService.updateTask(1, "title", LocalDateTime.now())).thenReturn(true);
        mockMvc.perform(post("/subproject/1/update-task/1")
                        .session(session)
                        .param("title", "test")
                        .param("deadline", "2024-12-18T08:30"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subproject/1/update-task/1"));
    }

    @Test
    void updateSubTask() throws Exception {
        when(projectService.getSubtask(1)).thenReturn(new Subtask(1, 1, "test", 3, 5, 2));
        mockMvc.perform(get("/task/1/update-subtask/1")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("update_subtask"))
                .andExpect(model().attributeExists("subtask"));
    }

    @Test
    void subTaskUpdated() throws Exception {
        when(projectService.updateSubTask(1, "test", 3, 2, 60, 5)).thenReturn(true);
        mockMvc.perform(post("/task/1/update-subtask/1")
                        .session(session)
                        .param("title", "test")
                        .param("estimatedHours", "3")
                        .param("CO2E", "2")
                        .param("percentageDone", "60"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1/update-subtask/1"));
    }


    @Test
    void toggleDarkMode() throws Exception {
        when(session.getAttribute("currentLocation")).thenReturn("/task/1");
        mockMvc.perform(post("/toggle-darkmode").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1"));
    }

    @Test
    void getCSS() throws Exception {
        mockMvc.perform(get("/css").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/darkmode.css"));

    }

    //co2e = 5
    @Test
    void changeSpentHours() throws Exception {
        when(projectService.getSubtask(1)).thenReturn(subtask);

        mockMvc.perform(get("/subtask/1/change-spenthours")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("update_spenthours"))
                .andExpect(model().attributeExists("subtask"))
                .andExpect(model().attribute("subtask", subtask));

    }

    @Test
    void changeCO2() throws Exception {
        when(projectService.getSubtask(1)).thenReturn(subtask);
        mockMvc.perform(get("/subtask/1/change-co2")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("update_co2"))
                .andExpect(model().attributeExists("subtask"))
                .andExpect(model().attribute("subtask", subtask));
    }

    @Test
    void saveSpentHoursExpectTrue() throws Exception {
        when(projectService.updateSpentHours(1, 2)).thenReturn(true);
        mockMvc.perform(post("/subtask/1/spenthours-changed")
                        .session(session)
                        .param("spent_time_hours", "2")
                        .param("spent_time_minutes", "0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subtask/1"));
    }

    @Test
    void saveSpentHoursExpectFalse() throws Exception {
        when(projectService.updateSpentHours(1, 3)).thenReturn(false);
        mockMvc.perform(post("/subtask/1/spenthours-changed")
                .session(session)
                .param("spent_time_hours","3")
                .param("spent_time_minutes","0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subtask/1/change-spenthours"));

    }

    @Test
    void saveCO2ExpectTrue() throws Exception {
        when(projectService.updateCO2e(1,5)).thenReturn(true);
        mockMvc.perform(post("/subtask/1/co2-changed")
                .session(session)
                .param("CO2e", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subtask/1"));
    }

    @Test
    void saveCO2ExpectFalse() throws Exception {
        when(projectService.updateCO2e(1,4)).thenReturn(false);
        mockMvc.perform(post("/subtask/1/co2-changed")
                .session(session)
                .param("CO2e", "4"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subtask/1/change-co2"));
    }

}

