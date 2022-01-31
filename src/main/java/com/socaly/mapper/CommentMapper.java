package com.socaly.mapper;

import com.socaly.dto.CommentDto;
import com.socaly.dto.CommentResponse;
import com.socaly.entity.*;
import com.socaly.repository.CommentVoteRepository;
import com.socaly.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Autowired
    private AuthService authService;

    @Autowired
    private CommentVoteRepository commentVoteRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentDto commentDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentDto mapToDto(Comment comment);
}
