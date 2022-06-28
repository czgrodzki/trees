package com.treesviewer.trees.service;

import com.treesviewer.trees.entity.Tree;

import java.util.List;

public interface TreeService {

    List<Tree> getAllTrees();
    void saveTree(Tree tree);
    Tree getTreeById(Long id);
    void deleteTree(Long id);

}
