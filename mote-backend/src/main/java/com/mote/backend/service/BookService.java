package com.mote.backend.service;

import com.mote.backend.model.Book;
import com.mote.backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    /**
     * Tenta encontrar o livro pelo ISBN. 
     * Se não existir, salva o novo livro no banco.
     * Isso garante que não teremos duplicatas do mesmo livro físico.
     */
    public Book findOrCreateBook(Book bookData) {
        Optional<Book> existingBook = bookRepository.findByIsbn(bookData.getIsbn());

        if (existingBook.isPresent()) {
            return existingBook.get(); // Retorna o que já está no banco (cache)
        } else {
            return bookRepository.save(bookData); // Salva o novo e retorna
        }
    }
}