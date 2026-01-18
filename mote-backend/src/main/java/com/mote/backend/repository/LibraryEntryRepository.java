package com.mote.backend.repository;

import com.mote.backend.model.LibraryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryEntryRepository extends JpaRepository<LibraryEntry, Long> {
    
    // "Me dê todos os livros da estante do usuário X"
    List<LibraryEntry> findByUserId(Long userId);

    // "Verifique se o usuário X já tem o livro Y na estante" (para evitar duplicatas)
    Optional<LibraryEntry> findByUserIdAndBookId(Long userId, Long bookId);
}