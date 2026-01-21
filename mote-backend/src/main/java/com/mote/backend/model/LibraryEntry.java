package com.mote.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // <--- Importante!
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

    @JsonIgnore // <--- ADICIONE ISSO
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ... restante do código (book, addedAt, motes) continua igual ...
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @CreationTimestamp
    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @OneToMany(mappedBy = "libraryEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mote> motes;
}