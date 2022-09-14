package com.treesviewer.trees.service.impl;

import com.treesviewer.trees.TreesApplication;
import com.treesviewer.trees.entity.User;
import com.treesviewer.trees.entity.dto.UserDto;
import com.treesviewer.trees.exception.OperationNotSupportedException;
import com.treesviewer.trees.exception.UserAlreadyExists;
import com.treesviewer.trees.exception.UserNotFoundException;
import com.treesviewer.trees.exception.ValidationException;
import com.treesviewer.trees.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(classes = TreesApplication.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TestUserServiceImpl {

    @Autowired
    private UserService userService;

    @Test
    void shouldAddUser() {
        // given
        UserDto userDto = new UserDto("name", "pass");

        // when
        userService.addUser(userDto);

        // then
        // There should be 2 items as ADMIN is initialized cause defined as a bean.
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void shouldThrowExceptionDueToUserWithGivenNameAlreadyExists() {
        // given
        UserDto userDto = new UserDto("name", "pass");
        UserDto userDto2 = new UserDto("name", "pass");

        // when
        userService.addUser(userDto);

        // then
        assertThrows(UserAlreadyExists.class, () -> userService.addUser(userDto2));
    }

    @Test
    void shouldThrowExceptionBecauseEmptyUserName() {
        // given
        UserDto userDto = new UserDto("", "pass");

        // when
        // then
        assertThrows(ValidationException.class, () -> userService.addUser(userDto));
    }


    @Test
    void shouldReturnUserByUsername() {
        // given
        UserDto userDto = new UserDto("name", "pass");
        userService.addUser(userDto);

        // when
        final User user = userService.getUserByUserName("name");

        // then
        assertEquals(userDto.getUsername(), user.getUsername());
    }

    @Test
    void shouldReturnUserById() {
        // given
        UserDto userDto = new UserDto("name", "pass");
        userService.addUser(userDto);

        // when
        final User user = userService.getUserByUserName("name");
        final User userById = userService.getUserById(user.getId());

        // hen
        assertEquals(user.getUsername(), userById.getUsername());
        assertEquals(user.getId(), userById.getId());
        assertEquals(user.getRole(), userById.getRole());
    }

    @Test
    void shouldThrowExceptionAsNoUserWithGivenId() {
        // given
        // when
        // then
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1000L));
    }

    @Test
    void shouldUpdateUser() {
        // given
        UserDto userDto = new UserDto("name", "pass");
        userService.addUser(userDto);
        final User user = userService.getUserByUserName("name");
        // when
        UserDto userDto2 = new UserDto("otherName", "pass");
        userService.updateUser(userDto2, user.getId());

        // then
        assertEquals("otherName", userService.getUserById(user.getId()).getUsername());
    }

    @Test
    void shouldThrowExceptionOnUpdateUserBecauseEmptyUsername() {
        // given
        UserDto userDto = new UserDto("name", "pass");
        userService.addUser(userDto);
        final User user = userService.getUserByUserName("name");
        final Long id = user.getId();
        UserDto userDto2 = new UserDto("", "pass");

        // when
        // then
        assertThrows(ValidationException.class, () -> userService.updateUser(userDto2, id));
    }

    @Test
    void shouldThrowExceptionOnUpdateUserBecauseCannotUpdateAdmin() {
        // given
        final User user = userService.getUserByUserName("admin");
        final Long id = user.getId();

        // when
        UserDto userDto2 = new UserDto("name", "pass");

        // then
        assertThrows(OperationNotSupportedException.class, () -> userService.updateUser(userDto2, id));
    }

    @Test
    void shouldDeleteUser() {
        // given
        UserDto userDto = new UserDto("name", "pass");
        userService.addUser(userDto);
        final User user = userService.getUserByUserName("name");
        final Long id = user.getId();
        // when
        userService.deleteUser(id);

        // then
        // There should be 1 items as ADMIN is initialized cause defined as a bean.
        assertEquals(1, userService.getAllUsers().size());
    }


    @Test
    void shouldThrowExceptionOnDeleteUserBecauseCannotDeleteAdmin() {
        // given
        final User user = userService.getUserByUserName("admin");
        final Long id = user.getId();

        // when
        // then
        assertThrows(OperationNotSupportedException.class, () -> userService.deleteUser(id));
    }

    @Test
    void shouldThrowExceptionOnDeleteAsNoUserWithGivenId() {
        // given
        // when
        // then
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1000L));
    }


}