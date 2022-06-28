package com.treesviewer.trees.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

@Entity
@Data
@Table(name="trees")
public class Tree {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    private User user;

}
