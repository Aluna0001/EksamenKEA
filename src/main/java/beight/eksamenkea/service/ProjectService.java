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

    public boolean login(String username, String password) {
        try {
            return passwordEncoder.matches(password, projectRepository.readPassword(username));
        } catch (EmptyResultDataAccessException e) {
            // Wrong username
            return false;
        }
    }

    public UserProfile readUserProfile(String username) {
        return projectRepository.readUserProfile(username);
    }

    public List<Project> getAllProjects() {
        return projectRepository.readAllProjects();
    }

    public Project getProject(int projectID) {
        return projectRepository.readProject(projectID);
    }

    public Subproject getSubproject(int subprojectID) {
        return projectRepository.readSubproject(subprojectID);
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
        return projectRepository.readTask(task_id);
    }

    public boolean updateTask(int taskID, String title, LocalDateTime deadline) {
        return projectRepository.updateTask(taskID, title, deadline);
    }

    public boolean updateSubTask(int taskID,
                                 String title,
                                 float estimatedHours,
                                 float CO2e,
                                 int percentageDone,
                                 float spentHours) {
        return projectRepository.updateSubTask(taskID, title, estimatedHours, CO2e,percentageDone, spentHours);
    }

    public boolean createSubTask(int taskID,
                                 String title,
                                 int estimated_time_hours,
                                 int estimated_time_minutes) {
        float estimatedHours = (estimated_time_hours + (estimated_time_minutes / 60f));
        return projectRepository.createSubtask(taskID, title, estimatedHours);
    }

    public String getTitle(String type, int id) {
        return projectRepository.readTitle(type, id);
    }

    public boolean updateTitle(String type, int id, String title) {
        return projectRepository.updateTitle(type, id, title);
    }

    public boolean delete(String type, int id, boolean confirm) {
        if (confirm) return projectRepository.delete(type, id);
        return false;
    }

    public Subtask getSubtask(int subtaskId) {
        return projectRepository.readSubtask(subtaskId);
    }

    public void toggleDarkMode(UserProfile userProfile) {
        if (projectRepository.toggleDarkMode(userProfile.getUsername(), !userProfile.isDarkmode())) {
            userProfile.toggleDarkMode();
        }
    }

    public boolean updateSpentHours(int id, float spentHours) {
        return projectRepository.updateSpentHours(id,spentHours);
    }

    public boolean updateCO2e(int id, float CO2e){
        return projectRepository.updateCO2e(id,CO2e);
    }





}


