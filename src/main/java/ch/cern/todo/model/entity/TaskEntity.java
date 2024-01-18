package ch.cern.todo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tasks")
@Data
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDateTime deadline;

    @OneToOne
    private TaskCategoryEntity category;
}
