package com.treesviewer.trees.service.impl;

import com.treesviewer.trees.TreesApplication;
import com.treesviewer.trees.entity.Tree;
import com.treesviewer.trees.entity.dto.TreeDto;
import com.treesviewer.trees.entity.dto.UserDto;
import com.treesviewer.trees.exception.TreeNotFoundException;
import com.treesviewer.trees.exception.ValidationException;
import com.treesviewer.trees.service.TreeService;
import com.treesviewer.trees.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(classes = TreesApplication.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TestTreeServiceImpl {

    @Autowired
    private UserService userService;

    @Autowired
    private TreeService treeService;


    @Test
    @WithMockUser(username = "name", authorities = {"USER"})
    void shouldSaveNewTree() {
        // given
        UserDto userDto = new UserDto("name", "password");
        userService.addUser(userDto);

        TreeDto treeDto = new TreeDto("name", "desc");
        treeService.saveTree(treeDto);
        TreeDto treeDto2 = new TreeDto("name2", "desc2");
        treeService.saveTree(treeDto2);

        // when
        final List<Tree> allTrees = treeService.getAllTrees();

        // then
        assertEquals(2, allTrees.size());

    }

    @Test
    @WithMockUser(username = "name", authorities = {"USER"})
    void shouldDeleteTree() {
        // given
        UserDto userDto = new UserDto("name", "password");
        userService.addUser(userDto);

        TreeDto treeDto = new TreeDto("name", "desc");
        treeService.saveTree(treeDto);
        TreeDto treeDto2 = new TreeDto("name2", "desc2");
        treeService.saveTree(treeDto2);

        // when
        final Tree treeByName = treeService.getTreeByName("name");
        treeService.deleteTree(treeByName.getId());

        final List<Tree> allTrees = treeService.getAllTrees();

        // then
        assertEquals(1, allTrees.size());

    }

    @Test
    @WithMockUser(username = "name", authorities = {"USER"})
    void shouldUpdateTree() {
        // given
        UserDto userDto = new UserDto("name", "password");
        userService.addUser(userDto);

        TreeDto treeDto = new TreeDto("name", "desc");
        treeService.saveTree(treeDto);
        TreeDto treeDto2 = new TreeDto("name2", "desc2");

        // when
        final Tree treeByName = treeService.getTreeByName("name");
        treeService.updateTree(treeDto2, treeByName.getId());

        final Tree treeByChangedName = treeService.getTreeByName("name2");

        // then
        assertEquals("name2", treeByChangedName.getName());
        assertEquals("desc2", treeByChangedName.getDescription());

    }

    @Test
    @WithMockUser(username = "name", authorities = {"USER"})
    void shouldThrowExceptionOnEmptyNameWhenUpdateTree() {
        // given
        UserDto userDto = new UserDto("name", "password");
        userService.addUser(userDto);

        TreeDto treeDto = new TreeDto("name", "desc");
        treeService.saveTree(treeDto);
        TreeDto treeDto2 = new TreeDto("", "desc2");

        // when
        final Tree treeByName = treeService.getTreeByName("name");
        final Long id = treeByName.getId();

        // then
        assertThrows(ValidationException.class, () -> treeService.updateTree(treeDto2, id));

    }

    @Test
    @WithMockUser(username = "name", authorities = {"USER"})
    void shouldThrowExceptionOnEmptyDescriptionWhenSavingTree() {
        // given
        UserDto userDto = new UserDto("name", "password");
        userService.addUser(userDto);

        TreeDto treeDto = new TreeDto("name", "");

        // when
        // then
        assertThrows(ValidationException.class, () -> treeService.saveTree(treeDto));

    }

    @Test
    @WithMockUser(username = "name", authorities = {"USER"})
    void shouldThrowExceptionWhenNoTeeById() {
        // given
        UserDto userDto = new UserDto("name", "password");
        userService.addUser(userDto);

        // when
        // then
        assertThrows(TreeNotFoundException.class, () -> treeService.getTreeById(1000L));

    }

    @Test
    @WithMockUser(username = "name", authorities = {"USER"})
    void shouldThrowExceptionWhenNoTreeToDelete() {
        // given
        UserDto userDto = new UserDto("name", "password");
        userService.addUser(userDto);

        // when
        // then
        assertThrows(TreeNotFoundException.class, () -> treeService.deleteTree(1000L));

    }

}