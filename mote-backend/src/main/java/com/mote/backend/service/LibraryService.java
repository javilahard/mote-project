package com.mote.backend.service;

import com.mote.backend.model.Book;
import com.mote.backend.model.LibraryEntry;
import com.mote.backend.model.User;
import com.mote.backend.repository.LibraryEntryRepository;
import com.mote.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryEntryRepository libraryEntryRepository;
    private final UserRepository userRepository;
    private final BookService bookService; // Injetamos o Service, não o Repository do livro!

    /**
     * Adiciona um livro à estante do usuário.
     * Fluxo:
     * 1. Busca o usuário.
     * 2. Busca ou Cria o livro no catálogo global (usando o BookService).
     * 3. Verifica se já não está na estante.
     * 4. Salva a relação.
     */
    public LibraryEntry addBookToShelf(Long userId, Book bookData) {
        // 1. Achar o usuário
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 2. Garantir que o livro existe no catálogo global (cache)
        Book book = bookService.findOrCreateBook(bookData);

        // 3. Verificação de Duplicidade (Regra de Negócio)
        Optional<LibraryEntry> existingEntry = libraryEntryRepository.findByUserIdAndBookId(userId, book.getId());
        if (existingEntry.isPresent()) {
            throw new RuntimeException("Este livro já está na sua estante!");
        }

        // 4. Criar a entrada na estante
        LibraryEntry entry = new LibraryEntry();
        entry.setUser(user);
        entry.setBook(book);
        
        return libraryEntryRepository.save(entry);
    }

    /**
     * Lista a estante de um usuário
     */
    public List<LibraryEntry> getUserShelf(Long userId) {
        return libraryEntryRepository.findByUserId(userId);
    }
}