package beight.eksamenkea.controller;

import beight.eksamenkea.model.Task;
import beight.eksamenkea.model.UserProfile;
import beight.eksamenkea.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ModelAttribute
    public void addAttributes(HttpServletRequest request,
                              @SessionAttribute UserProfile userProfile, // Important: triggers ServletRequestBindingException, see ExceptionControllerAdvice
                              Model model) {
        model.addAttribute("currentUrl", request.getRequestURI());
        model.addAttribute("darkModeIsOn", userProfile.darkModeIsOn());
    }

    @GetMapping("/portfolio")
    public String viewProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "projects";
    }

    @GetMapping("/project/{id}")
    public String viewProject(@PathVariable int id, Model model) {
        model.addAttribute("project", projectService.getProject(id));
        return "project";
    }

    @PostMapping("/toggle-darkmode")
    public String toggleDarkMode(@SessionAttribute UserProfile userProfile,
                                 @RequestParam String currentUrl,
                                 @RequestParam boolean switchToDarkMode) {
        projectService.toggleDarkMode(userProfile, switchToDarkMode);
        return "redirect:" + currentUrl;
    }

    @GetMapping("/css")
    public String getCSS(@SessionAttribute UserProfile userProfile) {
        if (userProfile.darkModeIsOn()) return "redirect:/darkmode.css";
        return "redirect:/lightmode.css";
    }

    @GetMapping("/create-project")
    public String createProject() {
        return "create_project";
    }

    @PostMapping("/project-created")
    public String saveNewProject(@RequestParam String title) {
        if (projectService.createProject(title)) return "redirect:/portfolio";
        return "redirect:/create-project";
    }

    @GetMapping("/subproject/{id}")
    public String viewSubproject(@PathVariable int id, Model model) {
        model.addAttribute("subproject", projectService.getSubproject(id));
        return "subproject";
    }

    @GetMapping("/project/{id}/create-subproject")
    public String createSubproject(@PathVariable int id, Model model) {
        model.addAttribute("projectID", id);
        return "create_subproject";
    }

    @PostMapping("/subproject-created")
    public String saveNewSubproject(@RequestParam int id,
                                    @RequestParam String title) {
        if (projectService.createSubproject(id, title)) return "redirect:/project/" + id;
        return "redirect:/project/" + id + "/create-subproject";
    }

    @GetMapping("/subproject/{id}/create-task")
    public String createTask(@PathVariable int id, Model model) {
        model.addAttribute("subprojectID", id);
        model.addAttribute("now", LocalDateTime.now().toString().substring(0, 16));
        return "create_task";
    }

    @PostMapping("/task-created")
    public String saveNewTask(@RequestParam int id,
                              @RequestParam String title,
                              @RequestParam LocalDateTime deadline) {
        if (projectService.createTask(id, title, deadline)) return "redirect:/subproject/" + id;
        return "redirect:/subproject/" + id + "/create-task";
    }

    @GetMapping("/task/{id}")
    public String readTask(@PathVariable int id, Model model) {
        model.addAttribute("task", projectService.getTask(id));
        return "task";
    }

    @GetMapping("/task/{id}/change-deadline")
    public String updateTask(@PathVariable int id,
                             Model model) {
        model.addAttribute("task", projectService.getTask(id));
        model.addAttribute("now", LocalDateTime.now().toString().substring(0, 16));
        return "update_task";
    }

    @PostMapping("/deadline-changed")
    public String taskUpdated(@RequestParam int id,
                              @RequestParam LocalDateTime deadline) {
        if (projectService.updateDeadline(id, deadline)) return "redirect:/task/" + id;
        return "redirect:/task/" + id + "/change-deadline";
    }

    @GetMapping("/task/{id}/create-subtask")
    public String createSubTask(Model model, @PathVariable int id) {
        model.addAttribute("taskID", id);
        return "create_subtask";
    }

    @PostMapping("/subtask-created")
    public String saveNewSubTask(@RequestParam int id,
                                 @RequestParam String title,
                                 @RequestParam int hours,
                                 @RequestParam int minutes){
        if (projectService.createSubTask(id, title, hours, minutes)) return "redirect:/task/" + id;
        return "redirect:/task/" + id + "/create-subtask";
    }

    @GetMapping("/subtask/{subtaskID}")
    public String viewSubtask(@PathVariable int subtaskID, Model model) {
        model.addAttribute("subtask", projectService.getSubtask(subtaskID));
        return "subtask";
    }

    @GetMapping("/subtask/{id}/{addOrReplace}-{estimatedOrSpent}-time")
    public String updateTime(@PathVariable int id,
                               @PathVariable String addOrReplace,
                               @PathVariable String estimatedOrSpent,
                               Model model) {
        if (!addOrReplace.equals("add") && !addOrReplace.equals("replace")) return "redirect:/subtask/" + id;
        model.addAttribute("add", addOrReplace.equals("add"));
        model.addAttribute("estimated", estimatedOrSpent.equals("estimated"));
        model.addAttribute("subtask",projectService.getSubtask(id));
        return "update_subtask";
    }

    @PostMapping("/time-changed")
    public String saveTime(@RequestParam int id,
                               @RequestParam boolean estimated,
                               @RequestParam boolean add,
                               @RequestParam int hours,
                               @RequestParam int minutes) {
        if (projectService.updateHours(id, hours, minutes, estimated, add)) return "redirect:/subtask/" + id;
        return "redirect:/subtask/" + id + "/" + (add ? "add" : "replace") +  "-" + (estimated ? "estimated" : "spent") + "-time";
    }

    @GetMapping("/subtask/{id}/{addOrReplace}-co2e")
    public String updateCO2e(@PathVariable int id,
                              @PathVariable String addOrReplace,
                              Model model) {
        if (!addOrReplace.equals("add") && !addOrReplace.equals("replace")) return "redirect:/subtask/" + id;
        model.addAttribute("add", addOrReplace.equals("add"));
        model.addAttribute("subtask", projectService.getSubtask(id));
        return "update_co2";
    }

    @PostMapping ("/co2e-changed")
    public String saveCO2e(@RequestParam int id,
                           @RequestParam boolean add,
                           @RequestParam float CO2e) {
        if (projectService.updateCO2e(id, CO2e, add)) return "redirect:/subtask/" + id;
        return "redirect:/subtask/" + id + "/" + (add ? "add" : "replace") + "-co2e";
    }

    @GetMapping("/{type}/{id}/change-title")
    public String changeTitle(@PathVariable String type,
                              @PathVariable int id,
                              Model model) {
        model.addAttribute("type", type);
        model.addAttribute("id", id);
        model.addAttribute("title", projectService.getTitle(type, id));
        return "update_title";
    }

    @PostMapping("/title-changed")
    public String saveTitle(@RequestParam String type,
                            @RequestParam int id,
                            @RequestParam String title) {
        if (projectService.updateTitle(type, id, title)) return "redirect:/" + type + "/" + id;
        return "redirect:/" + type + "/" + id + "/change-title";
    }

    @GetMapping("/{type}/{id}/delete")
    public String deleteOption(@PathVariable String type,
                               @PathVariable int id,
                               Model model) {
        model.addAttribute("type", type);
        model.addAttribute("id", id);
        model.addAttribute("title", projectService.getTitle(type, id));
        return "delete";
    }

    @PostMapping("/deleted")
    public String deleteConfirmation(@RequestParam String type,
                                     @RequestParam int id,
                                     @RequestParam(required = false) boolean confirm) {
        String url = projectService.constructReturnUrl(type, id);
        if (projectService.delete(type, id, confirm)) return "redirect:" + url;
        return "redirect:/" + type + "/" + id + "/delete";
    }
}

