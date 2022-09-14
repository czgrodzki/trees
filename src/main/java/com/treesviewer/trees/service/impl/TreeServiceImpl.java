package com.treesviewer.trees.service.impl;

import com.treesviewer.trees.entity.Role;
import com.treesviewer.trees.entity.Tree;
import com.treesviewer.trees.entity.User;
import com.treesviewer.trees.entity.dto.TreeDto;
import com.treesviewer.trees.entity.mapper.TreeMapper;
import com.treesviewer.trees.exception.TreeNotFoundException;
import com.treesviewer.trees.exception.ValidationException;
import com.treesviewer.trees.repository.TreeRepository;
import com.treesviewer.trees.service.TreeService;
import com.treesviewer.trees.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final UserService userService;

    @Override
    public List<Tree> getAllTrees() {
        final String loggedInUser = userService.getLoggedInUser();
        final User user = userService.getUserByUserName(loggedInUser);
        if (user.getRole().equals(Role.USER)) {
            return treeRepository.getAllTreesByUserId(user.getId());
        } else if (user.getRole().equals(Role.ADMIN)) {
            return treeRepository.findAll();
        }
        return treeRepository.findAll();
    }

    @Override
    public Tree getTreeByName(final String name) {
        return treeRepository.findByName(name);
    }

    @Override
    public Tree getTreeById(final Long id) {
        final Optional<Tree> optionalTree = treeRepository.findById(id);
        if (optionalTree.isEmpty()) {
            throw new TreeNotFoundException("No tree");
        }
        return optionalTree.get();
    }

    @Override
    public void saveTree(final TreeDto treeDto) {
        Tree tree = TreeMapper.map(treeDto);
        if (isNameAndPasswordNotNullAndNotEmpty(treeDto)) {
            final String loggedInUser = userService.getLoggedInUser();
            final User user = userService.getUserByUserName(loggedInUser);
            tree.setUser(user);
            treeRepository.save(tree);
        } else throw new ValidationException("Name and description must be filled");
    }

    @Override
    public void deleteTree(final Long id) {
        final Optional<Tree> optionalTree = treeRepository.findById(id);
        if (optionalTree.isEmpty()) {
            throw new TreeNotFoundException("No tree");
        }
        treeRepository.deleteById(optionalTree.get().getId());
    }

    @Override
    public void updateTree(final TreeDto treeDto, final Long treeId) {
        final Optional<Tree> optionalTree = treeRepository.findById(treeId);
        if (isNameAndPasswordNotNullAndNotEmpty(treeDto)) {
            if (optionalTree.isPresent()) {
                final Tree tree = optionalTree.get();
                tree.setName(treeDto.getName());
                tree.setDescription(treeDto.getDescription());
                treeRepository.save(tree);
            }
        } else throw new ValidationException("Name and description must be filled");
    }

    private boolean isNameAndPasswordNotNullAndNotEmpty(final TreeDto treeDto) {
        return treeDto.getName() != null && !treeDto.getName().isEmpty()
                && treeDto.getDescription() != null && !treeDto.getDescription().isEmpty();
    }
}
