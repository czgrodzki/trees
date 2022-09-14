package com.treesviewer.trees.security;

import com.treesviewer.trees.entity.User;
import com.treesviewer.trees.service.TreesUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(AdminConfig.class)
public class SecurityConfig {

    private final TreesUserDetailService treesUserDetailService;
    private final AdminConfig adminConfig;

    public SecurityConfig(final TreesUserDetailService treesUserDetailService, final AdminConfig adminConfig) {
        this.treesUserDetailService = treesUserDetailService;
        this.adminConfig = adminConfig;
    }

    @Bean
    User adminConfig(){
        return adminConfig.config();
    }

    @Bean
     BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(treesUserDetailService);
    }



    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user/add", "/user/save").permitAll()
                .antMatchers("/user/update*", "/user/delete/*").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().permitAll().loginPage("/")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/trees", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies()
                .clearAuthentication(true);

        return http.build();
    }



}

