package ch.cern.todo.exception.advice;

import ch.cern.todo.exception.model.ApiError;
import ch.cern.todo.exception.model.ResourceNotFoundException;
import ch.cern.todo.exception.model.ValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ResourceControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiError handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ApiError.builder()
                .message(exception.getLocalizedMessage())
                .errorCode(NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(NOT_ACCEPTABLE)
    public ApiError handleValidationException(ValidationException exception) {
        return ApiError.builder()
                .message(exception.getLocalizedMessage())
                .errorCode(NOT_ACCEPTABLE.value())
                .build();
    }
}
