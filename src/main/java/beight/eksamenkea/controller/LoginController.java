package beight.eksamenkea.controller;

import beight.eksamenkea.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final ProjectService projectService;

    public LoginController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String viewFrontpage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpSession session,
                        @RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        boolean passwordCorrect = false;
        boolean usernameCorrect = true;
        try {
            passwordCorrect = projectService.login(username, password);
        } catch (EmptyResultDataAccessException e) {
            usernameCorrect = false;
        }
        if (usernameCorrect && passwordCorrect) {
            session.setAttribute("userProfile", projectService.readUserProfile(username));
            return "redirect:/portfolio";
        }
        model.addAttribute("message", usernameCorrect ? "Invalid password." : "Invalid username.");
        model.addAttribute("username", username);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
