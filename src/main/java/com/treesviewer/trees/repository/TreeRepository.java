package com.treesviewer.trees.repository;

import com.treesviewer.trees.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {

    @Query("SELECT t FROM Tree t WHERE user_id = :userId")
    List<Tree> getAllTreesByUserId(final Long userId);

    Tree findByName(final String name);


}
