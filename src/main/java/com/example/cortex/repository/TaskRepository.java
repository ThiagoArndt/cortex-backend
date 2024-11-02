package com.example.cortex.repository;

import com.example.cortex.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.assignedTo WHERE t.group.groupId = :groupId")
    List<Task> findByGroup_groupIdWithAssignedUser(Integer groupId);
}
