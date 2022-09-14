package com.treesviewer.trees.service;

import com.treesviewer.trees.entity.Tree;
import com.treesviewer.trees.entity.dto.TreeDto;

import java.util.List;

public interface TreeService {

    List<Tree> getAllTrees();

    Tree getTreeByName(final String name);

    Tree getTreeById(final Long id);

    void saveTree(final TreeDto treeDto);

    void deleteTree(final Long id);

    void updateTree(final TreeDto treeDto, final Long treeId);

}
