package ch.cern.todo.model.filter;

import lombok.Builder;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.Pageable.ofSize;

@Builder
public record TaskFilters(
        String name,
        String description,
        String categoryName,
        String categoryDescription,
        LocalDateTime deadlineFrom,
        LocalDateTime deadlineTo,
        Pageable pageable
) {
    public TaskFilters {
        pageable = ofNullable(pageable)
                .orElseGet(() -> ofSize(20));
    }
}
