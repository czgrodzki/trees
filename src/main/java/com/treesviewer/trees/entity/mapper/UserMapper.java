package com.treesviewer.trees.entity.mapper;

import com.treesviewer.trees.entity.User;
import com.treesviewer.trees.entity.dto.UserDto;

public final class UserMapper {

    private UserMapper() {
    }

    public static User map(final UserDto userDto) {
        final User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
