package beight.eksamenkea.controller;

import beight.eksamenkea.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String viewFrontpage() {
        return "redirect:/project/1";
    }

    @GetMapping("/project/{id}")
    public String viewProject(@PathVariable int id, Model model) {
        model.addAttribute("project", projectService.getProject(id));
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

    @GetMapping("/subproject/{id}/create-task")
    public String createTask(@PathVariable int id, Model model) {
        model.addAttribute("subprojectID", id);
        return "create_task";
    }

    @PostMapping("/task-created")
    public String saveNewTask(@RequestParam int id,
                              @RequestParam String title,
                              @RequestParam LocalDateTime deadline) {
        if(projectService.createTask(id, title, deadline)) return "redirect:/subproject/" + id;
        return "redirect:/subproject/" + id + "/create-task";
    }


    @GetMapping("/task/{id}/create-subtask")
    public String createSubTask(Model model, @PathVariable int id) {
        model.addAttribute("taskID", id);
        return "create_subtask";
    }

    @PostMapping("/subtask-created")
    public String saveNewSubTask(@RequestParam int id,
                                 @RequestParam String title,
                                 @RequestParam(defaultValue = "0") int estimated_time_hours,
                                 @RequestParam(defaultValue = "0") int estimated_time_minutes) {
        if (projectService.createSubTask(id, title, estimated_time_hours, estimated_time_minutes)) return "redirect:/task/" + id;
        return "redirect:/task/" + id + "/create-subtask";
    }

    @GetMapping("/subtask/{id}")
    public String viewSubtask(@PathVariable int id, Model model) {
        model.addAttribute("subtask", projectService.getSubtask(id));
        return "subtask";
    }

    @GetMapping("/task/{task_id}")
    public String readTask(@PathVariable int task_id, Model model){
        model.addAttribute("task", projectService.getTask(task_id));
       return "task";
    }

    @GetMapping("/{type}/{id}/change-title")
    public String changeTitle(@PathVariable String type, @PathVariable int id, Model model) {
        model.addAttribute("type", type);
        model.addAttribute("id", id);
        model.addAttribute("title", projectService.getTitle(type, id));
        return "update_title";
    }

    @PostMapping("/title-changed")
    public String saveTitle(@RequestParam String type, @RequestParam int id, @RequestParam String title) {
        if (projectService.updateTitle(type, id, title)) return "redirect:/" + type + "/" + id;
        return "redirect:/" + type + "/" + id + "/change-title";
    }

}

