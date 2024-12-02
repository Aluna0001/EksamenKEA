package beight.eksamenkea.controller;

import beight.eksamenkea.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("project", projectService.getProject(1));
        return "project";
    }

    @GetMapping("/subproject/{id}")
    public String viewSubproject(@PathVariable int id, Model model) {
        model.addAttribute("subproject", projectService.getSubproject(id));
        return "subproject";
    }

    @GetMapping("/project/{id}/create-subproject")
    public String createSubproject(@PathVariable int id, Model model) {
        model.addAttribute("projectID", id);
        return "create-subproject";
    }

    @PostMapping("/subproject-created")
    public String saveNewSubproject(@RequestParam int id, @RequestParam String title) {
        if (projectService.createSubproject(id, title)) return "redirect:/";
        return "redirect:/project/" + id + "/create-subproject";
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

