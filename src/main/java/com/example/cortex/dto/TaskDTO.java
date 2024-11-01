package com.example.cortex.dto;

import com.example.cortex.model.TaskStatus;
import com.example.cortex.model.User;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class TaskDTO {
    private Integer taskId;
    private String taskName;
    private Integer groupId;
    private User assignedTo;
    private TaskStatus status;
    private LocalDate dueDate;
}
