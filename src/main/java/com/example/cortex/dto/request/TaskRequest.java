package com.example.cortex.dto.request;

import com.example.cortex.model.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaskRequest {
    private Integer taskId;
    private String taskName;
    private Integer groupId;
    private Integer assignedTo;
    private TaskStatus status;
    private LocalDate dueDate;
}



