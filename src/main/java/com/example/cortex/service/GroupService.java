package com.example.cortex.service;

import com.example.cortex.dto.GroupDTO;
import com.example.cortex.exception.CustomException;
import com.example.cortex.model.Group;
import com.example.cortex.model.Project;
import com.example.cortex.model.User;
import com.example.cortex.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final ProjectService projectService;
    private final UserService userService;
    public GroupDTO createGroup(GroupDTO groupDTO) {
        Project project = projectService.getProjectById(groupDTO.getProjectId());

        Group group = Group.builder()
                .groupName(groupDTO.getGroupName())
                .project(project)
                .build();

        group = groupRepository.save(group);
        return mapToDTO(group);
    }

    public List<GroupDTO> getProjectGroups(Integer projectId) {
        return groupRepository.findByProject_projectId(projectId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteGroup(Integer id) {
        Group group = getGroupById(id);
        userService.validateUserAccess(group.getProject());
        groupRepository.delete(group);
    }

    public GroupDTO updateGroup(Integer id, GroupDTO groupDTO) {
        Group group = getGroupById(id);

        group.setGroupName (groupDTO.getGroupName());

        group = groupRepository.save(group);
        return mapToDTO(group);
    }

    protected Group getGroupById(Integer id) {

        return groupRepository.findById(id)
                .orElseThrow(() -> new CustomException("Grupo não encontrado"));
    }

    private GroupDTO mapToDTO(Group group) {
        return GroupDTO.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .projectId(group.getProject().getProjectId())
                .build();
    }


}

