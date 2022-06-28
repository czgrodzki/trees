package com.treesviewer.trees.service.impl;

import com.treesviewer.trees.entity.User;
import com.treesviewer.trees.exception.UserNotFoundException;
import com.treesviewer.trees.repository.UserRepository;
import com.treesviewer.trees.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(final User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(final Long id) {
        final Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("There is no user with given id");
        }
        return optionalUser.get();
    }

    @Override
    public void deleteUser(final Long id) {
        final User userById = getUserById(id);
        userRepository.deleteById(id);
    }
}
