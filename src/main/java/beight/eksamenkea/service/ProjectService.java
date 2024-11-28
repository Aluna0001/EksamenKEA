package beight.eksamenkea.service;

import beight.eksamenkea.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public boolean createTask(String title, Date deadline){
        return projectRepository.createTask(title, deadline);
    }

}
