package com.socaly.mapper;

import com.socaly.dto.CommentDto;
import com.socaly.dto.CommentResponse;
import com.socaly.entity.*;
import com.socaly.repository.CommentVoteRepository;
import com.socaly.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Autowired
    private AuthService authService;

    @Autowired
    private CommentVoteRepository commentVoteRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "creationDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    public abstract Comment map(CommentDto commentDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    @Mapping(target = "upVote", expression = "java(isCommentUpVoted(comment))")
    @Mapping(target = "downVote", expression = "java(isCommentDownVoted(comment))")
    public abstract CommentResponse mapToDto(Comment comment);

    boolean isCommentUpVoted(Comment comment) {
        return checkVoteType(comment, VoteType.UPVOTE);
    }

    boolean isCommentDownVoted(Comment comment) {
        return checkVoteType(comment, VoteType.DOWNVOTE);
    }

    private boolean checkVoteType(Comment comment, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<CommentVote> voteForCommentByUser =
                    commentVoteRepository.findTopByCommentAndUserOrderByIdDesc(comment, authService.getCurrentUser());

            return voteForCommentByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
        }
        return false;
    }
}
