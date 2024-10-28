package com.example.cortex.service;

import com.example.cortex.dto.TaskDTO;
import com.example.cortex.exception.CustomException;
import com.example.cortex.model.Group;
import com.example.cortex.model.Task;
import com.example.cortex.model.User;
import com.example.cortex.repository.TaskRepository;
import com.example.cortex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final GroupService groupService;
    private final UserRepository userRepository;

    public TaskDTO createTask(TaskDTO taskDTO) {
        Group group = groupService.getGroupById(taskDTO.getGroupId());
        User assignedUser = taskDTO.getAssignedTo() != null ?
                userRepository.findById(taskDTO.getAssignedTo())
                        .orElseThrow(() -> new CustomException("Usuário atribuido não encontrado")) : null;

        Task task = Task.builder()
                .taskName(taskDTO.getTaskName())
                .group(group)
                .assignedTo(assignedUser)
                .status(taskDTO.getStatus())
                .dueDate(taskDTO.getDueDate())
                .build();

        task = taskRepository.save(task);
        return mapToDTO(task);
    }

    public List<TaskDTO> getGroupTasks(Integer groupId) {
        return taskRepository.findByGroup_groupId(groupId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO updateTask(Integer id, TaskDTO taskDTO) {
        Task task = getTaskById(id);

        if (taskDTO.getAssignedTo() != null) {
            User assignedUser = userRepository.findById(taskDTO.getAssignedTo())
                    .orElseThrow(() -> new CustomException("Usuário atribuido não encontrado"));
            task.setAssignedTo(assignedUser);
        }

        task.setTaskName(taskDTO.getTaskName());
        task.setStatus(taskDTO.getStatus());
        task.setDueDate(taskDTO.getDueDate());

        task = taskRepository.save(task);
        return mapToDTO(task);
    }

    public void deleteTask(Integer id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    private Task getTaskById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new CustomException("Tarefa não encontrada"));
    }

    private TaskDTO mapToDTO(Task task) {
        return TaskDTO.builder()
                .taskId(task.getTaskId())
                .taskName(task.getTaskName())
                .groupId(task.getGroup().getGroupId())
                .assignedTo(task.getAssignedTo() != null ? task.getAssignedTo().getUserId() : null)
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .build();
    }
}