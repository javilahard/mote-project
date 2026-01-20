package com.mote.backend.controller;

import com.mote.backend.dto.UserRequestDTO;
import com.mote.backend.model.User;
import com.mote.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestDTO dto) {
        // Convertendo DTO para Entidade (manualmente por enquanto para ser simples)
        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setEmail(dto.getEmail());

        User savedUser = userService.registerUser(newUser);
        return ResponseEntity.ok(savedUser);
    }
}