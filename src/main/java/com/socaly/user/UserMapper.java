package com.socaly.user;

import com.socaly.image.Image;
import com.socaly.userSettings.UserSettingsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileImage", expression = "java(mapStringToImage(userResponse.getProfileImage()))")
    public abstract User map(UserResponse userResponse);

    @Mapping(target = "profileImage", expression = "java(mapImageToString(user.getProfileImage()))")
    public abstract UserResponse mapToDto(User user);

    @Mapping(target = "profileImage", expression = "java(mapImageToString(user.getProfileImage()))")
    public abstract UserSettingsResponse mapToUserSettings(User user);

    Image mapStringToImage(String string) {
        Image image = new Image();
        image.setImageUrl(string);

        return image;
    }

    String mapImageToString(Image image) {
        if (image == null) return null;
        return image.getImageUrl();
    }
}
