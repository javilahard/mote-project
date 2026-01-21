package com.mote.backend.dto;

import com.mote.backend.model.MoteTheme;
import lombok.Data;
import java.util.Set;

@Data
public class MoteRequestDTO {
    private String content;     // O texto da anotação
    private String location;    // "Pág 42"
    private Set<MoteTheme> themes; // ["FUNNY", "INSIGHTFUL"]
}