package com.mote.backend.repository;

import com.mote.backend.model.Mote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoteRepository extends JpaRepository<Mote, Long> {
    
    // Busca todos os motes de um item específico da estante
    List<Mote> findByLibraryEntryId(Long libraryEntryId);
}