package ch.cern.todo.controller;

import ch.cern.todo.exception.model.ResourceNotFoundException;
import ch.cern.todo.model.dto.CategoryDto;
import ch.cern.todo.model.mapper.TaskCategoryMapper;
import ch.cern.todo.service.TaskCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TaskCategoryController {
    private final TaskCategoryService service;
    private final TaskCategoryMapper mapper;

    @GetMapping
    List<CategoryDto> findAll() {
        return mapper.toDto(service.findAll());
    }

    @GetMapping("{id}")
    CategoryDto findById(@PathVariable Long id) {
        return service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find category with id %s".formatted(id)));
    }

    @PostMapping
    CategoryDto add(@RequestBody CategoryDto category) {
        return mapper.toDto(service.add(mapper.toEntity(category)));
    }

    @DeleteMapping("{id}")
    CategoryDto deleteById(@PathVariable Long id) {
        return service.deleteById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find category with id %s".formatted(id)));
    }
}
