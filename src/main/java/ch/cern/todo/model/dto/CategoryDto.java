package ch.cern.todo.model.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record CategoryDto(
        Long id,
        String name,
        String description
) {
}
