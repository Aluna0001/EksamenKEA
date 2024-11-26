package beight.eksamenkea.controller;

import beight.eksamenkea.service.ProjectService;
import org.springframework.stereotype.Controller;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


}
