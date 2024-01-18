package ch.cern.todo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "task_categories")
@Data
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class TaskCategoryEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String name;
    private String description;
}
