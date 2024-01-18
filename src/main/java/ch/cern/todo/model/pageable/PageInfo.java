package ch.cern.todo.model.pageable;

import lombok.Builder;

@Builder
public record PageInfo(
        int crtPage,
        int pageSize,
        int totalPages,
        long totalElements) {
}
