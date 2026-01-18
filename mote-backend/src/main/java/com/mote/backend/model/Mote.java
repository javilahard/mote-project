package com.mote.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "motes")
@Data
public class Mote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false) // TEXT permite textos longos no MySQL
    private String content;

    @Column(nullable = false)
    private String location; // Ex: "Página 42", "Capítulo 3"

    @ElementCollection(targetClass = MoteTheme.class) // Cria uma tabela auxiliar simples para os temas
    @Enumerated(EnumType.STRING) // Salva como "FUNNY" no banco, não como número 0, 1...
    @CollectionTable(name = "mote_themes", joinColumns = @JoinColumn(name = "mote_id"))
    @Column(name = "theme")
    private Set<MoteTheme> themes; // Set evita temas duplicados no mesmo mote

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_entry_id", nullable = false)
    private LibraryEntry libraryEntry;

    @CreationTimestamp
    private LocalDateTime createdAt;
}