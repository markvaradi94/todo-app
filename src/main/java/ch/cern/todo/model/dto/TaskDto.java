package ch.cern.todo.model.dto;

import ch.cern.todo.config.deserializer.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;

@With
@Builder
public record TaskDto(
        Long id,
        String name,
        String description,
        @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
        LocalDateTime deadline,
        String categoryName,
        String categoryDescription
) {
}
