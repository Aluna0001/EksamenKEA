package beight.eksamenkea.controller;
import beight.eksamenkea.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

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

    @GetMapping("/create-task")
    public String createTask() {
        return "create_task";
    }

    @PostMapping("/task-created")
    public String saveNewTask(@RequestParam String title,
                              @RequestParam LocalDate deadline) {
        if(projectService.createTask(title, deadline)) return "redirect:/";
        return "redirect:/create-task";
    }


    @GetMapping("/create-sub-task")
    public String createSubTask() {
        return "create_sub_task";
    }

    @PostMapping("/sub-task-created")
    public String saveNewSubTask(@RequestParam String title,
                                 @RequestParam(defaultValue = "0") int estimated_time_hours,
                                 @RequestParam(defaultValue = "0") int estimated_time_minutes) {
        if (projectService.createSubTask(title, estimated_time_hours, estimated_time_minutes)) return "redirect:/";
        return "redirect:/create-sub-task";
    }

}

