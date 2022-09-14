package com.treesviewer.trees.controller;

import com.treesviewer.trees.entity.Tree;
import com.treesviewer.trees.entity.dto.TreeDto;
import com.treesviewer.trees.service.TreeService;
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
@RequestMapping("/trees")
@AllArgsConstructor
public class TreeController {

    private final TreeService treeService;

    @GetMapping
    public String getAllTrees(final Model model) {
        final List<Tree> allTrees = treeService.getAllTrees();
        model.addAttribute("trees", allTrees);
        return "trees";
    }

    @GetMapping("/add")
    public String addTree(final Model model) {
        model.addAttribute("tree", new Tree());

        return "new_tree";
    }

    @PostMapping("/save")
    public String saveTree(@ModelAttribute("tree") final TreeDto treeDto) {
        treeService.saveTree(treeDto);
        return "redirect:/trees";
    }

    @GetMapping("/update/{id}")
    public String updateTree(@PathVariable final Long id, final Model model) {
        final Tree treeById = treeService.getTreeById(id);
        model.addAttribute("tree", treeById);

        return "update_tree";
    }

    @PostMapping("/update_save/{id}")
    public String updatedTreeSave(@PathVariable final Long id, @ModelAttribute("tree") final TreeDto treeDto) {
        treeService.updateTree(treeDto, id);
        return "redirect:/trees";
    }

    @GetMapping("/delete/{id}")
    public String deleteTree(@PathVariable final Long id) {
        treeService.deleteTree(id);

        return "redirect:/trees";
    }

}
