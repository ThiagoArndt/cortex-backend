package com.example.cortex.repository;

import com.example.cortex.model.Project;
import com.example.cortex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByProjectUsers_User(User user);
}