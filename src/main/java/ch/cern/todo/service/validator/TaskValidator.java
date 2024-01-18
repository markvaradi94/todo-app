package ch.cern.todo.service.validator;

import ch.cern.todo.exception.model.ValidationException;
import ch.cern.todo.model.entity.TaskCategoryEntity;
import ch.cern.todo.model.entity.TaskEntity;
import ch.cern.todo.repository.TaskCategoryRepo;
import ch.cern.todo.repository.TaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Component
@RequiredArgsConstructor
public class TaskValidator {
    private final TaskRepo taskRepo;
    private final TaskCategoryRepo taskCategoryRepo;

    public void validateReplaceOrThrow(long taskId, TaskEntity newTask) {
        taskExists(taskId)
                .or(() -> validate(newTask, false))
                .ifPresent(this::throwValidationException);
    }

    public void validateNewOrThrow(TaskEntity task) {
        validate(task, true)
                .ifPresent(this::throwValidationException);
    }

    public void validateExistsOrThrow(long taskId) {
        taskExists(taskId)
                .ifPresent(this::throwValidationException);
    }

    private Optional<ValidationException> validate(TaskEntity task, boolean newEntity) {
        if (newEntity) {
            return validateNewTask(task);
        }

        return validateTaskCategory(task.getCategory());
    }

    private Optional<ValidationException> validateNewTask(TaskEntity task) {
        if (task.getDeadline().isBefore(LocalDateTime.now())) {
            return of(new ValidationException("Deadline cannot be in the past"));
        }

        if (task.getName() == null || task.getName().isBlank()) {
            return of(new ValidationException("Task name cannot be blank"));
        }

        return validateTaskCategory(task.getCategory());
    }

    private Optional<ValidationException> validateTaskCategory(TaskCategoryEntity category) {
        if (category.getName() == null || category.getName().isBlank()) {
            return of(new ValidationException("Category name cannot be blank"));
        }

        return empty();
    }

    private Optional<ValidationException> taskExists(long taskId) {
        return taskRepo.existsById(taskId)
                ? empty()
                : of(new ValidationException("Could not find task with id %s".formatted(taskId)));
    }

    private Optional<ValidationException> categoryExists(long categoryId) {
        return taskCategoryRepo.existsById(categoryId)
                ? empty()
                : of(new ValidationException("Could not find category with id %s".formatted(categoryId)));
    }

    private void throwValidationException(ValidationException ex) {
        throw ex;
    }
}
