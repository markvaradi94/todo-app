package ch.cern.todo.model.mapper;

import ch.cern.todo.model.dto.CategoryDto;
import ch.cern.todo.model.entity.TaskCategoryEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface TaskCategoryMapper {
    CategoryDto toDto(TaskCategoryEntity entity);

    TaskCategoryEntity toEntity(CategoryDto dto);

    default List<CategoryDto> toDto(List<TaskCategoryEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    default List<TaskCategoryEntity> toEntity(List<CategoryDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }
}
