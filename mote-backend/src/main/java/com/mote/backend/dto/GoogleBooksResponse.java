package com.mote.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

// Classe "Pai" que representa a resposta inteira
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos que não mapeamos
public class GoogleBooksResponse {
    private List<Item> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String id;
        private VolumeInfo volumeInfo;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VolumeInfo {
        private String title;
        private List<String> authors;
        private String description;
        private ImageLinks imageLinks;
        private List<IndustryIdentifier> industryIdentifiers;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageLinks {
        private String thumbnail;
        private String smallThumbnail;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IndustryIdentifier {
        private String type;       // ex: "ISBN_13"
        private String identifier; // ex: "97885..."
    }
}