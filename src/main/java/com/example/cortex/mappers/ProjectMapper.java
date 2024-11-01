package com.example.cortex.mappers;

import com.example.cortex.dto.ProjectDTO;
import com.example.cortex.dto.UserDTO;
import com.example.cortex.model.Project;
import com.example.cortex.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectDTO projectToUserDTO(Project project);
    Project projectDTOToUserDTO(ProjectDTO userDTO);
}
