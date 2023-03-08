package com.socaly.community;

import com.socaly.post.Post;
import com.socaly.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommunityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "posts", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "users", expression = "java(new java.util.ArrayList<>())")
    Community mapToCommunity(CommunityRequest communityDto, User user);

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(community.getPosts()))")
    @Mapping(target = "numberOfUsers", expression = "java(community.getUsers().size())")
    CommunityResponse mapCommunityToDto(Community community);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }
}
