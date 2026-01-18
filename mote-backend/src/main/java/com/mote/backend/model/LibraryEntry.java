package com.mote.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "library_entries")
@Data
public class LibraryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Muitos itens de estante pertencem a UM usuário
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Muitos itens de estante referenciam UM livro do catálogo
    @ManyToOne(fetch = FetchType.EAGER) // EAGER: Ao carregar a estante, já traga os dados do livro
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @CreationTimestamp // Preenche a data automaticamente quando salva
    @Column(name = "added_at")
    private LocalDateTime addedAt;

    // Se eu apagar o item da estante, apago os Motes associados (Cascade)
    @OneToMany(mappedBy = "libraryEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mote> motes;
}