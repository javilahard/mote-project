package com.mote.backend.controller;

import com.mote.backend.dto.BookRequestDTO;
import com.mote.backend.model.Book;
import com.mote.backend.model.LibraryEntry;
import com.mote.backend.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    // POST /api/library/{userId}/add
    // Exemplo de uso: Adicionar "Dom Casmurro" na estante do usuário ID 1
    @PostMapping("/{userId}/add")
    public ResponseEntity<LibraryEntry> addBookToShelf(
            @PathVariable Long userId,
            @RequestBody BookRequestDTO bookDto) {

        // 1. Converter DTO para Entidade Book
        Book book = new Book();
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setCoverUrl(bookDto.getCoverUrl());

        // 2. Chamar o serviço
        LibraryEntry entry = libraryService.addBookToShelf(userId, book);
        return ResponseEntity.ok(entry);
    }

    // GET /api/library/{userId}
    // Exemplo: Listar todos os livros do usuário 1
    @GetMapping("/{userId}")
    public ResponseEntity<List<LibraryEntry>> getUserShelf(@PathVariable Long userId) {
        List<LibraryEntry> shelf = libraryService.getUserShelf(userId);
        return ResponseEntity.ok(shelf);
    }
}