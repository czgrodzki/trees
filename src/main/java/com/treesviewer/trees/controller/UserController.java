package com.treesviewer.trees.controller;

import com.treesviewer.trees.entity.User;
import com.treesviewer.trees.entity.dto.UserDto;
import com.treesviewer.trees.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public String getAllUsers(final Model model) {
        final List<User> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);
        return "users";
    }

    @GetMapping("/add")
    public String addUser(final Model model) {
        model.addAttribute("user", new UserDto());

        return "registration";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") final UserDto userDto) {
        userService.addUser(userDto);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable final Long id) {
        userService.deleteUser(id);

        return "redirect:/trees";
    }

    @GetMapping("/update/{id}")
    public String updateTree(@PathVariable final Long id, final Model model) {
        final User userById = userService.getUserById(id);
        model.addAttribute("user", userById);

        return "update_user";
    }

    @PostMapping("/update_save/{id}")
    public String updatedUserSave(@PathVariable final Long id, @ModelAttribute("user") final UserDto userDto) {
        userService.updateUser(userDto, id);
        return "redirect:/trees";
    }

}
