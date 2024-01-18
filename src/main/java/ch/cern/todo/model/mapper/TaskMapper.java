package ch.cern.todo.model.mapper;

import ch.cern.todo.model.dto.TaskDto;
import ch.cern.todo.model.entity.TaskEntity;
import ch.cern.todo.model.pageable.CollectionResponse;
import ch.cern.todo.model.pageable.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "categoryName", source = "entity.category.name")
    @Mapping(target = "categoryDescription", source = "entity.category.description")
    TaskDto toDto(TaskEntity entity);

    @Mapping(target = "category.name", source = "dto.categoryName")
    @Mapping(target = "category.description", source = "dto.categoryDescription")
    TaskEntity toEntity(TaskDto dto);

    default List<TaskDto> toDto(Collection<TaskEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    default List<TaskEntity> toEntity(Collection<TaskDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }

    default CollectionResponse<TaskDto> toCollectionResponse(Page<TaskEntity> entityPage, Pageable pageable) {
        return CollectionResponse.<TaskDto>builder()
                .content(toDto(entityPage.getContent()))
                .pageInfo(PageInfo.builder()
                        .totalPages(entityPage.getTotalPages())
                        .totalElements(entityPage.getNumberOfElements())
                        .crtPage(pageable.getPageNumber())
                        .pageSize(pageable.getPageSize())
                        .build())
                .build();
    }
}
