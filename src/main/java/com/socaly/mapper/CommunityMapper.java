package com.socaly.mapper;

import com.socaly.dto.CommunityDto;
import com.socaly.entity.Community;
import com.socaly.entity.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommunityMapper {
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(community.getPosts()))")
    CommunityDto mapCommunityToDto(Community community);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Community mapDtoToCommunity(CommunityDto communityDto);
}
