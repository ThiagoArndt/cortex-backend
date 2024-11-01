package com.example.cortex.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectInviteRequest {
    private String email;
    private Integer projectId;
}
