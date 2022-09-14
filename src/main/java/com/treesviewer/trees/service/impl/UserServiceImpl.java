package com.treesviewer.trees.service.impl;

import com.treesviewer.trees.entity.Role;
import com.treesviewer.trees.entity.User;
import com.treesviewer.trees.entity.mapper.UserMapper;
import com.treesviewer.trees.entity.dto.UserDto;
import com.treesviewer.trees.exception.OperationNotSupportedException;
import com.treesviewer.trees.exception.UserAlreadyExists;
import com.treesviewer.trees.exception.UserNotFoundException;
import com.treesviewer.trees.exception.ValidationException;
import com.treesviewer.trees.repository.UserRepository;
import com.treesviewer.trees.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(final Long id) {
        final Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("No user");
        }
        return optionalUser.get();
    }

    @Override
    public void addUser(final UserDto userDto) {
        if (userAlreadyExist(userDto.getUsername())) {
            throw new UserAlreadyExists("Choose another username");
        }
        User user = UserMapper.map(userDto);
        if (isNameAndPasswordNotNullAndNotEmpty(userDto)) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setTrees(List.of());
            user.setRole(Role.USER);
            userRepository.save(user);
        } else throw new ValidationException("Username and password must be filled");
    }

    private boolean userAlreadyExist(final String username) {
        return userRepository.getUserByUsername(username).isPresent();
    }

    @Override
    public void deleteUser(final Long id) {
        final Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("No user");
        }
        if (optionalUser.get().getUsername().equals("admin")) {
            throw new OperationNotSupportedException("Admin cannot be deleted");
        }
        userRepository.deleteById(optionalUser.get().getId());
    }

    public String getLoggedInUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();

    }

    @Override
    public User getUserByUserName(final String userName) {
        final Optional<User> optionalUser = userRepository.getUserByUsername(userName);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("No user");
        }
        return optionalUser.get();
    }

    @Override
    public void updateUser(final UserDto userDto, final Long id) {
        final Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.get().getUsername().equals("admin")) {
            throw new OperationNotSupportedException("Admin cannot be updated");
        }
        final User user = optionalUser.get();
        if (isNameNotNullAndNotEmpty(userDto)) {
            user.setUsername(userDto.getUsername());
        } else throw new ValidationException("Name must be filled");
        userRepository.save(user);
    }

    private boolean isNameNotNullAndNotEmpty(final UserDto userDto) {
        return userDto.getUsername() != null && !userDto.getUsername().isEmpty();
    }

    private boolean isNameAndPasswordNotNullAndNotEmpty(final UserDto userDto) {
        return userDto.getUsername() != null && !userDto.getUsername().isEmpty()
                && userDto.getPassword() != null && !userDto.getPassword().isEmpty();
    }

}
