package com.example.cortex.repository;

import com.example.cortex.model.Comment;
import com.example.cortex.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}