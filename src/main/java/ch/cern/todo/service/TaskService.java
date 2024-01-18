package ch.cern.todo.service;

import ch.cern.todo.exception.model.ResourceNotFoundException;
import ch.cern.todo.model.entity.TaskCategoryEntity;
import ch.cern.todo.model.entity.TaskEntity;
import ch.cern.todo.model.filter.TaskFilters;
import ch.cern.todo.repository.TaskCategoryRepository;
import ch.cern.todo.repository.TaskRepository;
import ch.cern.todo.service.validator.TaskValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskValidator validator;
    private final TaskRepository taskRepository;
    private final TaskCategoryRepository categoryRepository;

    public Page<TaskEntity> findAll(TaskFilters filters) {
        return taskRepository.findAll(filters, filters.pageable());
    }

    public Optional<TaskEntity> findById(long id) {
        return taskRepository.findById(id);
    }

    public TaskEntity add(TaskEntity task) {
        validator.validateNewOrThrow(task);
        TaskCategoryEntity category = task.getCategory();
        TaskCategoryEntity categoryEntity = categoryRepository.findByName(category.getName())
                .orElseGet(() -> categoryRepository.add(category));

        TaskEntity taskEntity = TaskEntity.builder()
                .category(categoryEntity)
                .deadline(task.getDeadline())
                .description(task.getDescription())
                .name(task.getName())
                .build();

        return taskRepository.add(taskEntity);
    }

    public Optional<TaskEntity> deleteById(long id) {
        validator.validateExistsOrThrow(id);
        return taskRepository.deleteById(id);
    }

    public Optional<TaskEntity> updateById(long id, TaskEntity request) {
        TaskEntity taskEntity = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find task with id %s".formatted(id)));

        TaskCategoryEntity category = request.getCategory();
        TaskCategoryEntity categoryEntity;

        if (category.getName() != null) {
            categoryEntity = categoryRepository.findByName(category.getName())
                    .orElseGet(() -> categoryRepository.add(category));
        } else {
            categoryEntity = taskEntity.getCategory();
        }

        TaskEntity update = TaskEntity.builder()
                .category(categoryEntity != null ? categoryEntity : taskEntity.getCategory())
                .deadline(ofNullable(request.getDeadline()).orElse(taskEntity.getDeadline()))
                .description(ofNullable(request.getDescription()).orElse(taskEntity.getDescription()))
                .name(ofNullable(request.getName()).orElse(taskEntity.getName()))
                .build();

        validator.validateReplaceOrThrow(id, update);

        return taskRepository.updateById(id, update);
    }
}
