package com.example.cortex.service;

import com.example.cortex.dto.CommentDTO;
import com.example.cortex.exception.CustomException;
import com.example.cortex.mappers.CommentMapper;
import com.example.cortex.model.Comment;
import com.example.cortex.model.Task;
import com.example.cortex.model.User;
import com.example.cortex.repository.CommentRepository;
import com.example.cortex.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserService userService;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentDTO addComment(Integer id, CommentDTO data) {
        User currentUser = userService.getCurrentUser();


        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new CustomException("Tarefa não encontrada"));



        Comment comment = Comment.builder()
                .task(task)
                .user(currentUser)
                .content(data.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

        return commentMapper.toCommentDto(comment);
    }
}
