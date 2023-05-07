package com.socaly.userSettings;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSettingsMapper {
    UserSettingsResponse mapToUserSettingsResponse(UserSettings userSettings);
}
