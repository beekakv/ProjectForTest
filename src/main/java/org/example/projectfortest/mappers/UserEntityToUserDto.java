package org.example.projectfortest.mappers;

import org.example.projectfortest.dtos.UserDto;
import org.example.projectfortest.entity.UserEntity;

public class UserEntityToUserDto {
    public UserDto toUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setLogin(userEntity.getLogin());
        return userDto;
    }
}
