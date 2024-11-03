package com.example.cortex.mappers;

import com.example.cortex.dto.CommentDTO;
import com.example.cortex.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "user", source = "user")
    CommentDTO toCommentDto(Comment comment);
}