package ch.cern.todo.repository;

import ch.cern.todo.model.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<TaskEntity, Long> {
}
