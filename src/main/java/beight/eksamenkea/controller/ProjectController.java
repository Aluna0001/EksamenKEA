package beight.eksamenkea.controller;

import beight.eksamenkea.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

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

    @GetMapping("/create-subproject")
    public String createSubProject() {
        return "create-subproject";
    }

    @PostMapping("/save-subproject")
    public String saveNewSubProject(@RequestParam String title) {
        if (projectService.createSubProject(title)) return "redirect:/";
        return "redirect:/create-subproject";
    }


}

