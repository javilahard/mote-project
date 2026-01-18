package com.mote.backend.repository;

import com.mote.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Para verificar se já temos o livro no nosso catálogo antes de chamar a API
    Optional<Book> findByIsbn(String isbn);
}