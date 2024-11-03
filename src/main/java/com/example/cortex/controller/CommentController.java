package com.example.cortex.controller;

import com.example.cortex.dto.CommentDTO;
import com.example.cortex.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService taskService;

    @PostMapping("/add-comment/{taskId}")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Integer taskId, @RequestBody CommentDTO data) {
        return ResponseEntity.ok(taskService.addComment(taskId, data));
    }

    }