package beight.eksamenkea.service;

import beight.eksamenkea.model.Project;
import beight.eksamenkea.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getProject() {
        return projectRepository.readProject();
    }

    public boolean createSubTask(String title, int estimated_time_hours, int estimated_time_minutes) {
        float estimatedHours = (estimated_time_hours+(estimated_time_minutes/60f));
        return projectRepository.createSubTask(title, estimatedHours);
    }
}
