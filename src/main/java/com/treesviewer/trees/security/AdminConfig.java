package com.treesviewer.trees.security;

import com.treesviewer.trees.entity.User;
import com.treesviewer.trees.repository.UserRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import static com.treesviewer.trees.entity.Role.ADMIN;

@ConfigurationProperties("app.security.admin")
public final class AdminConfig {

    private static final String HASH_PASS = "$2a$12$Nsf4Cq8i7VDjHlUTa2QbWuGeonHkzRLYuqDnkPQP56oMDEBXqGlq6";
    private final UserRepository userRepository;


    AdminConfig(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User config() {
        final User user = new User("admin", HASH_PASS, List.of(), ADMIN);
        userRepository.save(user);
        return user;
    }
}
