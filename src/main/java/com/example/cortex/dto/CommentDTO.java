package com.example.cortex.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDTO {
    private Integer commentId;
    private UserDTO user;
    private String content;
    private LocalDateTime createdAt;
}



