package com.example.cortex.dto.response;

import com.example.cortex.dto.UserDTO;
import com.example.cortex.model.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaskResponse {
    private Integer taskId;
    private String taskName;
    private Integer groupId;
    private UserDTO assignedTo;
    private TaskStatus status;
    private LocalDate dueDate;
}