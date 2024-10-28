package com.example.cortex.service;

import com.example.cortex.dto.ProjectDTO;
import com.example.cortex.exception.CustomException;
import com.example.cortex.model.Project;
import com.example.cortex.model.ProjectUser;
import com.example.cortex.model.User;
import com.example.cortex.repository.ProjectRepository;
import com.example.cortex.repository.ProjectUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;
    private final UserService userService;

    public ProjectDTO createProject(ProjectDTO projectDTO) {
        User currentUser = userService.getCurrentUser();

        Project project = Project.builder()
                .projectName(projectDTO.getProjectName())
                .build();

        project = projectRepository.save(project);

        ProjectUser projectUser = ProjectUser.builder()
                .project(project)
                .user(currentUser)
                .build();

        projectUserRepository.save(projectUser);

        return mapToDTO(project);
    }

    public List<ProjectDTO> getUserProjects() {
        User currentUser = userService.getCurrentUser();
        return projectRepository.findByProjectUsers_User(currentUser)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ProjectDTO getProject(Integer id) {
        Project project = getProjectById(id);
        userService.validateUserAccess(project);
        return mapToDTO(project);
    }

    public void deleteProject(Integer id) {
        Project project = getProjectById(id);
        userService.validateUserAccess(project);
        projectRepository.delete(project);
    }




    public Project getProjectById(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new CustomException("Projeto não encontrado"));
    }



    private ProjectDTO mapToDTO(Project project) {
        return ProjectDTO.builder()
                .projectId(project.getProjectId())
                .projectName(project.getProjectName())
                .build();
    }
}