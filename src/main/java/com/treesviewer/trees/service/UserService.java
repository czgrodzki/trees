package com.treesviewer.trees.service;

import com.treesviewer.trees.entity.User;
import com.treesviewer.trees.entity.dto.UserDto;

import java.util.List;


public interface UserService {

    List<User> getAllUsers();
    User getUserById(final Long id);

    void addUser(final UserDto userDto);

    void deleteUser(final Long id);

    String getLoggedInUser();

    User getUserByUserName(final String userName);

    void updateUser(final UserDto userDto, final Long id);

}
