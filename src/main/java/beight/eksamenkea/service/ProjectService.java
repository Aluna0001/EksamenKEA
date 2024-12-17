package beight.eksamenkea.service;

import beight.eksamenkea.model.*;
import beight.eksamenkea.repository.ProjectRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean login(String username, String password) throws EmptyResultDataAccessException {
        return passwordEncoder.matches(password, projectRepository.readPassword(username));
    }

    public UserProfile readUserProfile(String username) {
        return projectRepository.readUserProfile(username);
    }

    public List<Project> getAllProjects() {
        return projectRepository.readAllProjects();
    }

    public Project getProject(int projectID) {
        Project project = projectRepository.readProject(projectID);
        if (project == null) throw new RuntimeException("Invalid path variable.");
        return project;
    }

    public Subproject getSubproject(int subprojectID) {
        Subproject subproject = projectRepository.readSubproject(subprojectID);
        if (subproject == null) throw new RuntimeException("Invalid path variable.");
        return subproject;
    }

    public boolean createProject(String title) {
        return projectRepository.createProject(title);
    }

    public boolean createSubproject(int projectID, String title) {
        return projectRepository.createSubproject(projectID, title);
    }

    public boolean createTask(int subprojectID, String title, LocalDateTime deadline) {
        return projectRepository.createTask(subprojectID, title, deadline);
    }

    public Task getTask(int task_id) {
        Task task = projectRepository.readTask(task_id);
        if (task == null) throw new RuntimeException("Invalid path variable.");
        return task;
    }

    public boolean updateDeadline(int taskID, LocalDateTime deadline) {
        return projectRepository.updateDeadline(taskID, deadline);
    }

    public boolean createSubTask(int taskID,
                                 String title,
                                 int hours,
                                 int minutes) {
        float estimatedHours = hours + (minutes / 60f);
        return projectRepository.createSubtask(taskID, title, estimatedHours);
    }

    public String getTitle(String type, int id) {
        String title = null;
        try {
            title = projectRepository.readTitle(type, id);
        } catch (EmptyResultDataAccessException e) {}
        if (title == null) throw new RuntimeException("Invalid path variable.");
        return title;
    }

    public boolean updateTitle(String type, int id, String title) {
        return projectRepository.updateTitle(type, id, title);
    }

    public boolean delete(String type, int id, boolean confirm) {
        if (confirm) return projectRepository.delete(type, id);
        return false;
    }

    public Subtask getSubtask(int subtaskId) {
        Subtask subtask = projectRepository.readSubtask(subtaskId);
        if (subtask == null) throw new RuntimeException("Invalid path variable.");
        return subtask;
    }

    public void toggleDarkMode(UserProfile userProfile, boolean switchToDarkMode) {
        if (projectRepository.toggleDarkMode(userProfile.getUsername(), switchToDarkMode)) {
            userProfile.toggleDarkMode(switchToDarkMode);
        }
    }

    public boolean updateHours(int id, int hours, int minutes, boolean estimated, boolean add) {
        float hoursDecimal = hours + (minutes / 60f);
        if (estimated && add) return projectRepository.addEstimatedHours(id, hoursDecimal);
        if (estimated) return projectRepository.updateEstimatedHours(id, hoursDecimal);
        if (add) return projectRepository.addSpentHours(id, hoursDecimal);
        return projectRepository.updateSpentHours(id, hoursDecimal);
    }

    public boolean updateCO2e(int id, float CO2e, boolean add) {
        if (add) return projectRepository.addCO2e(id, CO2e);
        return projectRepository.updateCO2e(id,CO2e);
    }

    public String constructReturnUrl(String type, int id) {
        String superType = switch (type) {
            case "subtask" -> "task";
            case "task" -> "subproject";
            case "subproject" -> "project";
            default -> "";
        };
        if (superType.isBlank()) return "/portfolio";
        return "/" + superType + "/" + projectRepository.readSuperID(id, type, superType);
    }

}


