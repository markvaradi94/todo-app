package ch.cern.todo.repository;

import ch.cern.todo.model.entity.TaskCategoryEntity;
import ch.cern.todo.model.entity.TaskEntity;
import ch.cern.todo.model.filter.TaskFilters;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ch.cern.todo.model.entity.TaskCategoryEntity.Fields.name;
import static ch.cern.todo.model.entity.TaskEntity.Fields.*;
import static java.util.Optional.ofNullable;

@Repository
public class TaskRepository {
    private final TaskRepo repo;
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public TaskRepository(TaskRepo repo, EntityManager entityManager) {
        this.repo = repo;
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<TaskEntity> findAll(TaskFilters filters, Pageable pageable) {
        CriteriaQuery<TaskEntity> criteria = criteriaBuilder.createQuery(TaskEntity.class);
        Root<TaskEntity> root = criteria.from(TaskEntity.class);
        Join<TaskEntity, TaskCategoryEntity> categories = root.join(category);
        List<Predicate> predicates = buildPredicates(filters, root, categories);
        CriteriaQuery<TaskEntity> query = criteria.select(root).where(predicates.toArray(new Predicate[0]));
        List<TaskEntity> tasks = entityManager.createQuery(query).getResultList();

        return new PageImpl<>(tasks, pageable, tasks.size());
    }

    public Optional<TaskEntity> findById(long id) {
        return repo.findById(id);
    }

    public TaskEntity add(TaskEntity task) {
        return repo.save(task);
    }

    public Optional<TaskEntity> deleteById(long id) {
        Optional<TaskEntity> taskToDelete = findById(id);
        taskToDelete.ifPresent(repo::delete);
        return taskToDelete;
    }

    public Optional<TaskEntity> updateById(long id, TaskEntity source) {
        Optional<TaskEntity> taskToUpdate = findById(id);
        taskToUpdate.ifPresent(target -> updateTask(source, target));
        return taskToUpdate;
    }

    private void updateTask(TaskEntity task, TaskEntity target) {
        target.setName(task.getName());
        target.setDescription(task.getDescription());
        target.setDeadline(task.getDeadline());
        target.setCategory(task.getCategory());
        repo.save(target);
    }

    private List<Predicate> buildPredicates(TaskFilters filters, Root<TaskEntity> root, Join<TaskEntity, TaskCategoryEntity> categories) {
        List<Predicate> predicates = new ArrayList<>();
        ofNullable(filters.name())
                .ifPresent(value -> predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(TaskEntity.Fields.name)), filterFormat(value))));
        ofNullable(filters.description())
                .ifPresent(value -> predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(description)), filterFormat(value))));
        ofNullable(filters.categoryName())
                .ifPresent(value -> predicates.add(criteriaBuilder.like(criteriaBuilder.lower(categories.get(name)), filterFormat(value))));
        ofNullable(filters.categoryDescription())
                .ifPresent(value -> predicates.add(criteriaBuilder.like(criteriaBuilder.lower(categories.get(TaskCategoryEntity.Fields.description)), filterFormat(value))));
        ofNullable(filters.deadlineFrom())
                .ifPresent(value -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(deadline), value)));
        ofNullable(filters.deadlineTo())
                .ifPresent(value -> predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(deadline), value)));
        return predicates;
    }

    private String filterFormat(String value) {
        return "%" + value.toLowerCase() + "%";
    }
}
