package beight.eksamenkea.service;

import beight.eksamenkea.model.Project;
import beight.eksamenkea.model.Subproject;
import beight.eksamenkea.model.Task;
import beight.eksamenkea.model.Subtask;
import beight.eksamenkea.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getProject(int projectID) {
        return projectRepository.readProject(projectID);
    }

    public Subproject getSubproject(int subprojectID) {
        return projectRepository.readSubproject(subprojectID);
    }

    public boolean createSubproject(int projectID, String title) {
        return projectRepository.createSubproject(projectID, title);
    }

    public boolean createTask(int subprojectID, String title, LocalDateTime deadline) {
        return projectRepository.createTask(subprojectID, title, deadline);
    }

    public Task getTask(int task_id){
        return projectRepository.readTask(task_id);
    }

    public boolean updateTask(int taskID, String title, LocalDateTime deadline) {
        return projectRepository.updateTask(taskID,title,deadline);
    }

    public boolean updateSubTask(int taskID, String title, float estimatedHours) {
        return projectRepository.updateSubTask(taskID,title,estimatedHours);
    }

    public boolean createSubTask(int taskID, String title, int estimated_time_hours, int estimated_time_minutes) {
        float estimatedHours = (estimated_time_hours+(estimated_time_minutes/60f));
        return projectRepository.createSubtask(taskID, title, estimatedHours);
    }

    public boolean deleteTask(int taskID, String confirm) {
        if(confirm.equals("on")){
            return projectRepository.deleteTask(taskID);
        }
        return false;
    }

    public String getTitle(String type, int id) {
        return projectRepository.readTitle(type, id);
    }

    public boolean updateTitle(String type, int id, String title) {
        return projectRepository.updateTitle(type, id, title);
    }


    public Subtask getSubtask(int subtaskId) {
        return projectRepository.readSubtask(subtaskId);
    }

    public boolean deleteSubTask(int subtaskID, String confirm) {
        if(confirm.equals("on")){
            return projectRepository.deleteSubTask(subtaskID);
        }
        return false;
    }

}
