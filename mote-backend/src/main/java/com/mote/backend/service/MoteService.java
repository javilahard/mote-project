package com.mote.backend.service;

import com.mote.backend.dto.MoteRequestDTO;
import com.mote.backend.model.LibraryEntry;
import com.mote.backend.model.Mote;
import com.mote.backend.repository.LibraryEntryRepository;
import com.mote.backend.repository.MoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoteService {

    private final MoteRepository moteRepository;
    private final LibraryEntryRepository libraryEntryRepository;

    public Mote createMote(Long userId, Long bookId, MoteRequestDTO dto) {
        // 1. Verificar se o livro está na estante do usuário
        // Buscamos a "ligação" (LibraryEntry) entre esse User e esse Book
        LibraryEntry entry = libraryEntryRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado na estante. Adicione-o primeiro!"));

        // 2. Criar o Mote
        Mote mote = new Mote();
        mote.setContent(dto.getContent());
        mote.setLocation(dto.getLocation());
        mote.setThemes(dto.getThemes());
        mote.setLibraryEntry(entry); // Aqui fazemos a amarração final!

        // 3. Salvar no banco
        return moteRepository.save(mote);
    }
}