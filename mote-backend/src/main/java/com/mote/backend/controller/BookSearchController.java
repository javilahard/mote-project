package com.mote.backend.controller;

import com.mote.backend.dto.BookRequestDTO;
import com.mote.backend.service.GoogleBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookSearchController {

    private final GoogleBooksService googleBooksService;

    // GET /api/books/search?q=Harry+Potter
    @GetMapping("/search")
    public ResponseEntity<List<BookRequestDTO>> searchBooks(@RequestParam("q") String query) {
        List<BookRequestDTO> results = googleBooksService.searchBooks(query);
        return ResponseEntity.ok(results);
    }
}