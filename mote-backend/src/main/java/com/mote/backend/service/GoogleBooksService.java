package com.mote.backend.service;

import com.mote.backend.dto.BookRequestDTO;
import com.mote.backend.dto.GoogleBooksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoogleBooksService {

    private final RestTemplate restTemplate;

    // URL base da API pública (sem chave por enquanto)
    private final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public List<BookRequestDTO> searchBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }

        // 1. Faz a chamada HTTP para o Google
        String url = GOOGLE_BOOKS_API_URL + query.replace(" ", "+");
        GoogleBooksResponse response = restTemplate.getForObject(url, GoogleBooksResponse.class);

        // 2. Verifica se voltou algo
        if (response == null || response.getItems() == null) {
            return Collections.emptyList();
        }

        // 3. Converte a bagunça do Google nos nossos DTOs limpinhos
        return response.getItems().stream()
                .map(this::convertToDTO)
                .filter(dto -> dto.getIsbn() != null) // Remove livros sem ISBN (não servem pra gente)
                .collect(Collectors.toList());
    }

    private BookRequestDTO convertToDTO(GoogleBooksResponse.Item item) {
        GoogleBooksResponse.VolumeInfo info = item.getVolumeInfo();
        BookRequestDTO dto = new BookRequestDTO();

        dto.setTitle(info.getTitle());
        
        // Pega o primeiro autor ou deixa null
        if (info.getAuthors() != null && !info.getAuthors().isEmpty()) {
            dto.setAuthor(info.getAuthors().get(0));
        }

        // Tenta pegar a capa (thumbnail)
        if (info.getImageLinks() != null) {
            dto.setCoverUrl(info.getImageLinks().getThumbnail());
        }

        // Lógica para achar o ISBN (prioriza ISBN_13, senão pega ISBN_10)
        if (info.getIndustryIdentifiers() != null) {
            String isbn = info.getIndustryIdentifiers().stream()
                    .filter(id -> "ISBN_13".equals(id.getType()))
                    .map(GoogleBooksResponse.IndustryIdentifier::getIdentifier)
                    .findFirst()
                    .orElse(
                        info.getIndustryIdentifiers().stream()
                            .filter(id -> "ISBN_10".equals(id.getType()))
                            .map(GoogleBooksResponse.IndustryIdentifier::getIdentifier)
                            .findFirst()
                            .orElse(null)
                    );
            dto.setIsbn(isbn);
        }

        return dto;
    }
}