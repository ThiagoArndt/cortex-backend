package com.example.cortex.repository;

import com.example.cortex.model.Project;
import com.example.cortex.model.ProjectUser;
import com.example.cortex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Integer> {
    boolean existsByProjectAndUser(Project project, User user);

    ProjectUser findByProjectProjectIdAndUserUserId(Integer projectId, Integer userId);

    long countByProjectProjectId(Integer projectId);

    List<ProjectUser> findByProjectProjectId(Integer projectId);
}
