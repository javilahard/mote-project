package com.mote.backend.repository;

import com.mote.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // A Mágica do Spring Data:
    // Só de declarar esse método, o Spring já sabe que deve fazer um:
    // "SELECT * FROM users WHERE email = ?"
    Optional<User> findByEmail(String email);
    
    // O mesmo aqui. Ele cria a query baseado no nome do método!
    boolean existsByUsername(String username);
}