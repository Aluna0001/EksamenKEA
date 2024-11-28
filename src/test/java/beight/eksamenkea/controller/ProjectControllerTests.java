package beight.eksamenkea.controller;

import beight.eksamenkea.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;


    @Test
    void createTask() throws Exception {
        mockMvc.perform(get("create-task"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-task"));
    }

//    @Test
//    void saveNewTask() throws Exception {
//        mockMvc.perform(post("save-task"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("save-task"));
//
//    }

}
