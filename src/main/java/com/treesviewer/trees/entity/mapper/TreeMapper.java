package com.treesviewer.trees.entity.mapper;

import com.treesviewer.trees.entity.Tree;
import com.treesviewer.trees.entity.dto.TreeDto;
import com.treesviewer.trees.exception.ValidationException;

public final class TreeMapper {

    private TreeMapper() {
    }

    public static Tree map(final TreeDto treeDto) {
        if (isNullOrEmpty(treeDto)) {
            throw new ValidationException("Name and Description fields need to be filled");
        }
        final Tree tree = new Tree();
        tree.setName(treeDto.getName());
        tree.setDescription(treeDto.getDescription());
        return tree;
    }

    private static boolean isNullOrEmpty(final TreeDto treeDto) {
        return treeDto.getName() == null || treeDto.getName().isEmpty()
                || treeDto.getDescription() == null || treeDto.getDescription().isEmpty();
    }
}
