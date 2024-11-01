package com.example.cortex.mappers;

import com.example.cortex.dto.GroupDTO;
import com.example.cortex.dto.TaskDTO;
import com.example.cortex.model.Group;
import com.example.cortex.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);
    GroupDTO groupToGroupDTO(Group group);
    Group groupDTOToGroup(GroupDTO groupDTO);
}