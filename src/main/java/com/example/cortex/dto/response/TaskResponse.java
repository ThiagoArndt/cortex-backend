package com.example.cortex.dto.response;

import com.example.cortex.dto.CommentDTO;
import com.example.cortex.dto.UserDTO;
import com.example.cortex.model.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TaskResponse {
    private Integer taskId;
    private String taskName;
    private Integer groupId;
    private UserDTO assignedTo;
    private TaskStatus status;
    private LocalDate dueDate;
    private List<CommentDTO> comments;
}