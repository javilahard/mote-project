package com.mote.backend.model;

import jakarta.persistence.*;
import lombok.Data; // O Lombok gera Getters, Setters, toString, etc. automagicamente

@Entity
@Table(name = "books")
@Data 
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // ISBN único é crucial
    private String isbn;

    @Column(nullable = false)
    private String title;

    private String author;

    @Column(name = "cover_url") // Mapeia para cover_url no banco (snake_case)
    private String coverUrl;
    
    // Opcional: Número de páginas, editora, data de publicação, etc.
}
