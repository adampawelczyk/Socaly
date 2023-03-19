package com.socaly.community;

import com.socaly.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommunityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "users", expression = "java(new java.util.ArrayList<>())")
    Community mapToCommunity(CommunityRequest communityDto, User user);

    @Mapping(target = "numberOfUsers", expression = "java(community.getUsers().size())")
    CommunityResponse mapToCommunityResponse(Community community);
}
