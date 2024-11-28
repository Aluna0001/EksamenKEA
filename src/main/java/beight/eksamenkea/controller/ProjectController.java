package beight.eksamenkea.controller;
import beight.eksamenkea.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;

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

    @GetMapping("/create-task")
    public String createTask() {
        return "create_task";
    }

    @PostMapping("/task-created")
    public String saveNewTask(@RequestParam String title,
                              @RequestParam Date deadline) {
        if(projectService.createTask(title, deadline)) return "subproject";
        return "redirect:/create-task";
    }

}
