package com.treesviewer.trees.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Tree> trees = List.of();

    @CollectionTable(
            name = "useres_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = Set.of();

    User(final String login, final String password, final List<Tree> trees) {
        this.login = login;
        this.password = password;
        this.trees = trees;
        this.roles = Set.of("ROLE_USER");
    }

}
