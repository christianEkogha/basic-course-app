package fr.cekogha.coursemanager.dto;

import java.time.LocalDateTime;
import java.util.List;

public record EventDTO(EventType type, List<?> content, LocalDateTime createAt) {
}
