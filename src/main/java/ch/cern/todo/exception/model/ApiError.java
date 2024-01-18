package ch.cern.todo.exception.model;

import lombok.Builder;

@Builder
public record ApiError(
        String message,
        Integer errorCode) {
}
