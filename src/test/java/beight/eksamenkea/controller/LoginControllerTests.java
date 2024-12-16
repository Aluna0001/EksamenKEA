package beight.eksamenkea.controller;

import beight.eksamenkea.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
class LoginControllerTests {

    @Autowired MockMvc mockMvc;
    @MockitoBean ProjectService projectService;

    @Test
    void viewFrontpage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void loginPass() throws Exception {
        when(projectService.login("admin", "bondegaard")).thenReturn(true);
        mockMvc.perform(post("/login")
                        .param("username", "admin")
                        .param("password", "bondegaard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/portfolio"));
    }

    @Test
    void loginFail() throws Exception {
        when(projectService.login("admin", "bondegard")).thenReturn(false);
        mockMvc.perform(post("/login")
                        .param("username", "admin")
                        .param("password", "bondegard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void logout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

}
