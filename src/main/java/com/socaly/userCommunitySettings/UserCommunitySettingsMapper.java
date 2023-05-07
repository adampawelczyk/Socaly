package com.socaly.userCommunitySettings;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCommunitySettingsMapper {
    UserCommunitySettingsResponse mapToUserCommunitySettingsResponse(UserCommunitySettings userCommunitySettings);
}
