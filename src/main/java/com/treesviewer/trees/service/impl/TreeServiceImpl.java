package com.treesviewer.trees.service.impl;

import com.treesviewer.trees.entity.Tree;
import com.treesviewer.trees.exception.TreeNotFoundException;
import com.treesviewer.trees.exception.UserNotFoundException;
import com.treesviewer.trees.repository.TreeRepository;
import com.treesviewer.trees.service.TreeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TreeServiceImpl implements TreeService {

    private TreeRepository treeRepository;

    @Override
    public List<Tree> getAllTrees() {
        return treeRepository.findAll();
    }

    @Override
    public void saveTree(final Tree tree) {
        treeRepository.save(tree);
    }

    @Override
    public Tree getTreeById(final Long id) {
        final Optional<Tree> optionalTree = treeRepository.findById(id);
        if (optionalTree.isEmpty()) {
            throw new TreeNotFoundException("There is no tree with given id");
        }
        return optionalTree.get();
    }

    @Override
    public void deleteTree(final Long id) {
        final Tree treeById = getTreeById(id);
        treeRepository.deleteById(treeById.getId());
    }
}
