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

    public boolean createSubProject(String title) {
        return projectRepository.createSubProject(title);
    }

}
