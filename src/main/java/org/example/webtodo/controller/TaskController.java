package org.example.webtodo.controller;


import jakarta.validation.Valid;
import org.example.webtodo.dto.TaskCreateRequest;
import org.example.webtodo.dto.TaskResponse;
import lombok.AllArgsConstructor;


import org.example.webtodo.dto.TaskUpdateRequest;
import org.example.webtodo.mapper.TaskMapper;
import org.example.webtodo.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.webtodo.service.TaskService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable Long id) {
        if (id <= 0) throw new IllegalArgumentException("Wrong id");
        return taskMapper.toResponse(taskService.get(id));
    }
    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody @Valid TaskCreateRequest request) {
        Task entity = taskMapper.fromCreate(request);
        Task saved = taskService.create(entity);
        return ResponseEntity
                .created(URI.create("/api/tasks/" + saved.getId()))
                .body(taskMapper.toResponse(saved));
    }

    @GetMapping
    public List<TaskResponse> list(@RequestParam(required = false) String status,
                                   @RequestParam(defaultValue = "dueAt") String sortBy,
                                   @RequestParam(defaultValue = "asc") String sortDir) {
        return taskService.list(status,sortBy,sortDir).stream().map(taskMapper::toResponse).toList();

    }
    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id,
                               @RequestBody @Valid TaskUpdateRequest request) {
        Task current = taskService.get(id);
        taskMapper.update(current, request);
        Task saved = taskService.update(id, current);

        return taskMapper.toResponse(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
