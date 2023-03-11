package com.socaly.post;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.socaly.community.Community;
import com.socaly.comment.CommentRepository;
import com.socaly.image.Image;
import com.socaly.util.VoteType;
import com.socaly.vote.Vote;
import com.socaly.vote.VoteRepository;
import com.socaly.auth.AuthService;
import com.socaly.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private VoteRepository voteRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "images", expression = "java(mapStringsToImages(postRequest.getImages()))")
    public abstract Post mapToPost(PostRequest postRequest, Community community, User user);

    @Mapping(target = "communityName", source = "community.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    @Mapping(target = "images", expression = "java(mapImagesToStrings(post.getImages()))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, VoteType.UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, VoteType.DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());

            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
        }
        return false;
    }

    List<Image> mapStringsToImages(List<String> strings) {
        List<Image> images = new ArrayList<>();
        for (String string : strings) {
            Image image = new Image();
            image.setImageUrl(string);
            images.add(image);
        }
        return images;
    }

    List<String> mapImagesToStrings(List<Image> images) {
        List<String> strings = new ArrayList<>();
        for (Image image : images) {
            strings.add(image.getImageUrl());
        }
        return strings;
    }
}
