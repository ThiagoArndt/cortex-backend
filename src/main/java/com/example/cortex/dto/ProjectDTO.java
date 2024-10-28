package com.example.cortex.dto;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ProjectDTO {
    private Integer projectId;
    private String projectName;
}
