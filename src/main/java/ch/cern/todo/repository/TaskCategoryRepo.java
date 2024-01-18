package ch.cern.todo.repository;

import ch.cern.todo.model.entity.TaskCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskCategoryRepo extends JpaRepository<TaskCategoryEntity, Long> {
    Optional<TaskCategoryEntity> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
