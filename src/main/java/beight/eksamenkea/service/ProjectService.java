package beight.eksamenkea.service;

import beight.eksamenkea.model.Project;
import beight.eksamenkea.model.Subproject;
import beight.eksamenkea.model.Task;
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

    public boolean createTask(int subproject_id, String title, LocalDateTime deadline) {
        return projectRepository.createTask(subproject_id, title, deadline);
    }

    public boolean createSubTask(String title, int estimated_time_hours, int estimated_time_minutes) {
        float estimatedHours = (estimated_time_hours+(estimated_time_minutes/60f));
        return projectRepository.createSubTask(title, estimatedHours);
    }

    public Task getTask(int task_id){
        return projectRepository.readTask(task_id);
    }

    public String getTitle(String type, int id) {
        return projectRepository.readTitle(type, id);
    }

    public boolean updateTitle(String type, int id, String title) {
        return projectRepository.updateTitle(type, id, title);
    }


    public Subproject editSubProject(String subprojectName, String subprojectDescription, float subprojectEstimatedTime) {
        return projectRepository.editSubProject(subprojectName, subprojectDescription, subprojectEstimatedTime);
    }
}
