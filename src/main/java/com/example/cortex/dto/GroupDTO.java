package com.example.cortex.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDTO {
    private Integer groupId;
    private String groupName;
    private Integer projectId;
}
