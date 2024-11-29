package beight.eksamenkea.service;

import beight.eksamenkea.model.Project;
import beight.eksamenkea.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getProject() {
        return projectRepository.readProject();
    }

    public boolean createTask(String title, LocalDate deadline) {
        return projectRepository.createTask(title, deadline);
    }

    public boolean createSubTask(String title, int estimated_time_hours, int estimated_time_minutes) {
        float estimatedHours = (estimated_time_hours+(estimated_time_minutes/60f));
        return projectRepository.createSubTask(title, estimatedHours);
    }
}
