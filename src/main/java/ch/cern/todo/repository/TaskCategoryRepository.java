package ch.cern.todo.repository;

import ch.cern.todo.model.entity.TaskCategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskCategoryRepository {
    private final TaskCategoryRepo repo;

    public Optional<TaskCategoryEntity> findByName(String name) {
        return repo.findByNameIgnoreCase(name);
    }

    public TaskCategoryEntity add(TaskCategoryEntity category) {
        return repo.save(category);
    }
}
