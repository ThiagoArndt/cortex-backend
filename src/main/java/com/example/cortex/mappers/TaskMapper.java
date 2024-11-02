package com.example.cortex.mappers;
import com.example.cortex.dto.response.TaskResponse;
import com.example.cortex.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "group.groupId", target = "groupId")
    @Mapping(source = "assignedTo.userId", target = "assignedTo.userId")
    @Mapping(source = "assignedTo.email", target = "assignedTo.email")
    @Mapping(source = "assignedTo.username", target = "assignedTo.username")
    TaskResponse taskToTaskResponse(Task task);





}


