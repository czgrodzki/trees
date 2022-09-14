package com.treesviewer.trees.service;

import com.treesviewer.trees.entity.Role;
import com.treesviewer.trees.entity.User;
import com.treesviewer.trees.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class TreesUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public TreesUserDetailService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<User> userByUsername = userRepository.getUserByUsername(username);
        return userByUsername.map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        parseRoles(user.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));

    }

    private Collection<? extends GrantedAuthority> parseRoles(Role roles) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roles.name()));

    }

}
