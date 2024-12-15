package beight.eksamenkea.controller;

import beight.eksamenkea.model.UserProfile;
import beight.eksamenkea.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public void addAttributes(HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("userProfile") == null) throw new RuntimeException("Not logged in.");
        String url = request.getRequestURI();
        //gem ikke seneste location som en af de her i session
        if (!url.equals("/css") && !url.equals("/toggle-darkmode")) {
            session.setAttribute("currentLocation", url);
        }
    }

    @GetMapping("/projects")
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
                                 @SessionAttribute String currentLocation) {
        projectService.toggleDarkMode(userProfile);
        //redirecter til der hvor den kommer fra og ikke "toggle-darkmode"
        return "redirect:" + currentLocation;

    }

    @GetMapping("/css")
    public String getCSS(@SessionAttribute UserProfile userProfile) {
        if (userProfile.isDarkmode()) {
            return "redirect:/darkmode.css";
        } else {
            return "redirect:/lightmode.css";
        }
    }

    @GetMapping("/create-project")
    public String createProject() {
        return "create_project";
    }

    @PostMapping("/project-created")
    public String saveNewProject(@RequestParam String title) {
        if (projectService.createProject(title)) return "redirect:/projects";
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
        return "create_task";
    }

    @PostMapping("/task-created")
    public String saveNewTask(@RequestParam int id,
                              @RequestParam String title,
                              @RequestParam LocalDateTime deadline) {
        if (projectService.createTask(id, title, deadline)) return "redirect:/subproject/" + id;
        if (projectService.updateTask(id, title, deadline)) return "redirect:/subproject/" + id + "/subprojectID";
        return "redirect:/subproject/" + id + "/create-task";
    }

    @GetMapping("/task/{task_id}")
    public String readTask(@PathVariable int task_id, Model model) {
        model.addAttribute("task", projectService.getTask(task_id));
        return "task";
    }

    @GetMapping("/subproject/{subprojectID}/update-task/{taskID}")
    public String updateTask(@PathVariable int subprojectID,
                             @PathVariable int taskID,
                             Model model) {
        model.addAttribute("task", projectService.getTask(taskID));
        model.addAttribute("subprojectID", subprojectID);
        return "update_task";
    }

    @PostMapping("/subproject/{subprojectID}/update-task/{taskID}")
    public String taskUpdated(@PathVariable int subprojectID,
                              @PathVariable int taskID,
                              @RequestParam String title,
                              @RequestParam LocalDateTime deadline) {
        if (projectService.updateTask(taskID, title, deadline)) return "redirect:/subproject/" + subprojectID;
        return "redirect:/subproject/" + subprojectID + "/update-task/" + taskID;
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
                                 @RequestParam(defaultValue = "0") int estimated_time_minutes){
        if (projectService.createSubTask(id, title, estimated_time_hours, estimated_time_minutes))
            return "redirect:/task/" + id;
        return "redirect:/task/" + id + "/create-subtask";
    }

    @GetMapping("/subtask/{subtaskID}")
    public String viewSubtask(@PathVariable int subtaskID, Model model) {
        model.addAttribute("subtask", projectService.getSubtask(subtaskID));
        return "subtask";
    }

    @GetMapping("/task/{taskID}/update-subtask/{subtaskID}")
    public String updateSubTask(@PathVariable int taskID,
                                @PathVariable int subtaskID,
                                Model model) {
        model.addAttribute("subtask", projectService.getSubtask(subtaskID));
        model.addAttribute("taskID", taskID);
        return "update_subtask";
    }

    @PostMapping("/task/{taskID}/update-subtask/{subtaskID}")
    public String subTaskUpdated(@PathVariable int taskID,
                                 @PathVariable int subtaskID,
                                 @RequestParam String title,
                                 @RequestParam(defaultValue = "0") int estimated_time_hours,
                                 @RequestParam(defaultValue = "0") int estimated_time_minutes,
                                 @RequestParam(defaultValue = "0") int percentage_done,
                                 @RequestParam (defaultValue = "0") float coe2,
                                 @RequestParam(defaultValue = "0") int spent_time_hours,
                                 @RequestParam(defaultValue = "0") int spent_time_minutes) {
        float estimated_hours = estimated_time_hours + (estimated_time_minutes / 60.0f);
        float spent_hours = spent_time_hours + (spent_time_minutes / 60.0f);
        if (projectService.updateSubTask(taskID, title,
                estimated_hours, coe2,percentage_done,spent_hours)) return "redirect:/task/" + taskID;
        return "redirect:/task/" + taskID + "/update-subtask/" + subtaskID;
    }

    //spent hours
    @GetMapping("/subtask/{subtaskID}/change-spenthours")
    public String changeSpentHours(@PathVariable int subtaskID, Model model) {
        model.addAttribute("id",subtaskID);
        model.addAttribute("subtask",projectService.getSubtask(subtaskID));
        return "update_spenthours";
    }

    @PostMapping("/subtask/{subtaskID}/spenthours-changed")
    public String saveSpentHours(@PathVariable int subtaskID,
                                 @RequestParam(defaultValue = "0") int spent_time_hours,
                                 @RequestParam(defaultValue = "0") int spent_time_minutes) {
        float spentHours = spent_time_hours + (spent_time_minutes / 60.0f);
        boolean updated = projectService.updateSpentHours(subtaskID, spentHours);
        if (updated) {
            return "redirect:/subtask/" + subtaskID;
        } else {
            return "redirect:/subtask/" + subtaskID + "/change-spenthours";
        }

    }

    //co2e
    @GetMapping("/subtask/{subtaskID}/change-co2")
    public String changeCO2(@PathVariable int subtaskID, Model model) {
        model.addAttribute("id",subtaskID);
        model.addAttribute("subtask", projectService.getSubtask(subtaskID));
        return "update_co2";
    }

    @PostMapping ("/subtask/{subtaskID}/co2-changed")
    public String saveCO2(@PathVariable int subtaskID, @RequestParam float CO2e){
        boolean updated = projectService.updateCO2e(subtaskID, CO2e);
        if (updated) {
            return "redirect:/subtask/" + subtaskID;
        }
        return "redirect:/subtask/" + subtaskID + "/change-co2";
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

    @GetMapping("/{supertype}/{superid}/{type}/{id}/delete")
    public String deleteOption(@PathVariable String supertype,
                               @PathVariable int superid,
                               @PathVariable String type,
                               @PathVariable int id,
                               Model model) {
        model.addAttribute("url", "/" + supertype + "/" + superid);
        model.addAttribute("type", type);
        model.addAttribute("id", id);
        model.addAttribute("title", projectService.getTitle(type, id));
        return "delete";
    }

    @GetMapping("/project/{id}/delete")
    public String deleteProject(@PathVariable int id, Model model) {
        model.addAttribute("url", "/projects");
        model.addAttribute("type", "project");
        model.addAttribute("id", id);
        model.addAttribute("title", projectService.getTitle("project", id));
        return "delete";
    }

    @PostMapping("/deleted")
    public String deleteConfirmation(@RequestParam String url,
                                     @RequestParam String type,
                                     @RequestParam int id,
                                     @RequestParam(required = false) boolean confirm) {
        if (projectService.delete(type, id, confirm)) return "redirect:" + url;
        return "redirect:" + url + "/" + type + "/" + id + "/delete";
    }
}

