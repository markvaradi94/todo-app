package ch.cern.todo.service;

import ch.cern.todo.exception.model.ValidationException;
import ch.cern.todo.model.entity.TaskCategoryEntity;
import ch.cern.todo.repository.TaskCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskCategoryService {
    private final TaskCategoryRepository repository;

    public List<TaskCategoryEntity> findAll() {
        return repository.findAll();
    }

    public Optional<TaskCategoryEntity> findById(long id) {
        return repository.findById(id);
    }

    public TaskCategoryEntity add(TaskCategoryEntity category) {
        if (repository.existsByName(category.getName())) {
            throw new ValidationException("Category with name %s already exists".formatted(category.getName()));
        }
        return repository.add(category);
    }

    public Optional<TaskCategoryEntity> deleteById(long id) {
        var categoryToDelete = repository.findById(id);
        categoryToDelete.ifPresent(repository::delete);
        return categoryToDelete;
    }
}
