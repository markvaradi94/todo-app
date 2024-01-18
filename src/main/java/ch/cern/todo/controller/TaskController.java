package ch.cern.todo.controller;

import ch.cern.todo.exception.model.ResourceNotFoundException;
import ch.cern.todo.model.dto.TaskDto;
import ch.cern.todo.model.filter.TaskFilters;
import ch.cern.todo.model.mapper.TaskMapper;
import ch.cern.todo.model.pageable.CollectionResponse;
import ch.cern.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;
    private final TaskMapper mapper;

    @GetMapping
    CollectionResponse<TaskDto> findAll(TaskFilters filters) {
        return mapper.toCollectionResponse(service.findAll(filters), filters.pageable());
    }

    @GetMapping("{id}")
    TaskDto findById(@PathVariable long id) {
        return service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find task with id %s".formatted(id)));
    }

    @PostMapping
    TaskDto add(@RequestBody TaskDto task) {
        return mapper.toDto(service.add(mapper.toEntity(task)));
    }

    @DeleteMapping("{id}")
    TaskDto deleteById(@PathVariable long id) {
        return service.deleteById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find task with id %s".formatted(id)));
    }

    @PutMapping("{id}")
    TaskDto updateById(@PathVariable long id, @RequestBody TaskDto task) {
        return service.updateById(id, mapper.toEntity(task))
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find task with id %s".formatted(id)));
    }
}
