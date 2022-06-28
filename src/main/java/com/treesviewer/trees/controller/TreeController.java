package com.treesviewer.trees.controller;

import com.treesviewer.trees.entity.Tree;
import com.treesviewer.trees.service.TreeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@AllArgsConstructor
public class TreeController {

    private final TreeService treeService;

    @GetMapping("/")
    public String viewHomePage(final Model model) {
        model.addAttribute("trees", treeService.getAllTrees());
        return "home";
    }

    @GetMapping("/add")
    public String showNewTreeForm(final Model model) {
        final Tree tree = new Tree();
        model.addAttribute("tree", tree);
        return "new_tree";
    }

    @PostMapping("/save")
    public String saveTree(@ModelAttribute("tree") final Tree tree) {
        treeService.saveTree(tree);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateForm(@PathVariable(value = "id") final Long id, final Model model) {
        final Tree treeById = treeService.getTreeById(id);
        model.addAttribute("tree", treeById);
        return "update";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTree(@PathVariable(name = "id") final Long id) {
        treeService.deleteTree(id);
        return "redirect:/";
    }

}
