package com.example.cortex.mappers;
import com.example.cortex.dto.TaskDTO;
import com.example.cortex.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "group.groupId", target = "groupId")
    TaskDTO taskToTaskDTO(Task task);

    @Mapping(source = "groupId", target = "group.groupId")
    Task taskDTOToTask(TaskDTO taskDTO);

}


