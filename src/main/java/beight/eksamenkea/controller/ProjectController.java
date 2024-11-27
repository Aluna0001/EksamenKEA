package beight.eksamenkea.controller;

import beight.eksamenkea.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String viewFrontpage(Model model) {
        model.addAttribute("project", projectService.getProject());
        return "project";
    }
}
