package com.mote.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // <--- Importante!
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    // Corte o ciclo aqui!
    @JsonIgnore // <--- ADICIONE ISSO
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibraryEntry> libraryEntries;
}