package com.treesviewer.trees.service;

import com.treesviewer.trees.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(Long id);
    void deleteUser(Long id);
}
