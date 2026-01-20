package com.mote.backend.service;

import com.mote.backend.model.User;
import com.mote.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // O Lombok cria o construtor para injetar o Repository automaticamente
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(User user) {
        // Regra de Negócio 1: Username único
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Este nome de usuário já está em uso.");
        }

        // Regra de Negócio 2: Email único
        // Note que o findByEmail retorna um Optional, então verificamos se está presente
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Este e-mail já está cadastrado.");
        }

        return userRepository.save(user);
    }
    
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }
}