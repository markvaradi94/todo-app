package ch.cern.todo.repository;

import ch.cern.todo.model.entity.TaskCategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskCategoryRepository {
    private final TaskCategoryRepo repo;

    public List<TaskCategoryEntity> findAll() {
        return repo.findAll();
    }

    public Optional<TaskCategoryEntity> findByName(String name) {
        return repo.findByNameIgnoreCase(name);
    }

    public Optional<TaskCategoryEntity> findById(long id) {
        return repo.findById(id);
    }

    public TaskCategoryEntity add(TaskCategoryEntity category) {
        return repo.save(category);
    }

    public boolean existsByName(String name) {
        return repo.existsByNameIgnoreCase(name);
    }

    public void delete(TaskCategoryEntity category) {
        repo.delete(category);
    }
}
