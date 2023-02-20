package com.socaly.mapper;

import com.socaly.dto.UserDto;
import com.socaly.entity.Image;
import com.socaly.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileImage", expression = "java(mapStringToImage(userDto.getProfileImage()))")
    public abstract User map(UserDto userDto);

    @Mapping(target = "profileImage", expression = "java(mapImageToString(user.getProfileImage()))")
    public abstract UserDto mapToDto(User user);

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
