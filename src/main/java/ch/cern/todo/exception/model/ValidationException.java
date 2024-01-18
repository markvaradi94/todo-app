package ch.cern.todo.exception.model;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
