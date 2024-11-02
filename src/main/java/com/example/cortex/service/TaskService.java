package com.example.cortex.service;

import com.example.cortex.dto.request.TaskRequest;
import com.example.cortex.dto.response.TaskResponse;
import com.example.cortex.exception.CustomException;
import com.example.cortex.mappers.TaskMapper;
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
    private final TaskMapper taskMapper; // Injecting TaskMapper

    public TaskResponse createTask(TaskRequest taskDTO) {
        Group group = groupService.getGroupById(taskDTO.getGroupId());

        Task task = Task.builder()
                .taskName(taskDTO.getTaskName())
                .group(group)
                .assignedTo(null)
                .status(taskDTO.getStatus())
                .dueDate(taskDTO.getDueDate())
                .build();

        task = taskRepository.save(task);
        return taskMapper.taskToTaskResponse(task);
    }

    public List<TaskResponse> getGroupTasks(Integer groupId) {
        List<Task> tasks = taskRepository.findByGroup_groupIdWithAssignedUser(groupId);



        return tasks.stream()
                .map(taskMapper::taskToTaskResponse)
                .collect(Collectors.toList());
    }


    public TaskResponse updateTask(Integer id, TaskRequest taskDTO) {
        Task task = getTaskById(id);
        User assignedUser = null;

        if(taskDTO.getAssignedTo() != null){
            assignedUser = userRepository.findById(taskDTO.getAssignedTo())
                    .orElseThrow(() -> new CustomException("Usuário atribuido não encontrado"));
        }



        task.setAssignedTo(assignedUser);
        task.setTaskName(taskDTO.getTaskName());
        task.setStatus(taskDTO.getStatus());
        task.setDueDate(taskDTO.getDueDate());
        task.setGroup(groupService.getGroupById(taskDTO.getGroupId()));

        task = taskRepository.save(task);
        return taskMapper.taskToTaskResponse(task);
    }

    public void deleteTask(Integer id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    private Task getTaskById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new CustomException("Tarefa não encontrada"));
    }


}