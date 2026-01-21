package com.mote.backend.controller;

import com.mote.backend.dto.MoteRequestDTO;
import com.mote.backend.model.Mote;
import com.mote.backend.service.MoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/motes")
@RequiredArgsConstructor
public class MoteController {

    private final MoteService moteService;

    // POST /api/motes/{userId}/{bookId}
    // Exemplo: Criar anotação para o Usuário 1 no Livro 5
    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<Mote> createMote(
            @PathVariable Long userId,
            @PathVariable Long bookId,
            @RequestBody MoteRequestDTO dto) {

        Mote createdMote = moteService.createMote(userId, bookId, dto);
        return ResponseEntity.ok(createdMote);
    }
}