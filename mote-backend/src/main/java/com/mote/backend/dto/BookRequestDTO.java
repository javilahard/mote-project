package com.mote.backend.dto;

import lombok.Data;

@Data
public class BookRequestDTO {
    private String isbn;
    private String title;
    private String author;
    private String coverUrl;
}