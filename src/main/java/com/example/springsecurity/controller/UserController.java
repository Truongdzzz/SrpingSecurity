package com.example.springsecurity.controller;

import com.example.springsecurity.model.user;
import jdk.jfr.Enabled;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableMethodSecurity
public class UserController {

    private final List<user> users;

    public UserController() {
        users = new ArrayList<>();
        user user1 = new user();
        user1.setId(1);
        user1.setUsername("john_doe");
        user1.setPassword("password123");
        user1.setRole("ROLE_USER");
        user1.setEmail("john.doe@example.com");

        user user2 = new user();
        user2.setId(2);
        user2.setUsername("jane_doe");
        user2.setPassword("password456");
        user2.setRole("ROLE_ADMIN");
        user2.setEmail("jane.doe@example.com");

        users.add(user1);
        users.add(user2);
    }

    @GetMapping("/user/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<user>> getAllUsers() {
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<user> getUserById(@PathVariable("id") int id) {
        List<user> filteredUsers = users.stream()
                .filter(user -> user.getId() == id)
                .collect(Collectors.toList());

        if (filteredUsers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(filteredUsers.get(0));
    }
}
