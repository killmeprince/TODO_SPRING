package org.example.webtodo.service;


import lombok.RequiredArgsConstructor;
import org.example.webtodo.model.Status;
import org.example.webtodo.model.Task;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.webtodo.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService implements Taskable {
    private final TaskRepository repository;

    @Override
    public Task create(Task task) {
        if (task == null) throw new IllegalArgumentException("No task data");
        if (task.getStatus() == null) task.setStatus(Status.TODO);
        return repository.save(task);
    }

    @Override
    public Task update(Long id, Task task) {
        if (task == null) throw new IllegalArgumentException("No task data");
        Optional<Task> changed = repository.findById(id);
        if (changed.isEmpty()) throw new RuntimeException("No task data");
        Task found = changed.get();
        found.setName(task.getName());
        found.setInfo(task.getInfo());
        found.setStatus(task.getStatus());
        found.setDueAt(task.getDueAt() != null ? task.getDueAt() : found.getDueAt());
        return repository.save(found);
    }

    @Override
    public void delete(Long id) {
        Optional<Task> task = repository.findById(id);
        if (task.isEmpty()) throw new RuntimeException("Cannot find");
        Task found = task.get();
        repository.delete(found);
    }

    @Transactional(readOnly = true)
    @Override
    public Task get(Long id) {
        Optional<Task> found = repository.findById(id);
        if (found.isPresent()) return found.get();
        else throw new RuntimeException("No user found");
    }

    @Override
    public List<Task> list() {
        if (repository.findAll().isEmpty()) {
            return Collections.emptyList();
        }
        return repository.findAll();
    }

    @Override
    public List<Task> list(Status status) {
        if (repository.findAllByStatus(status).isEmpty()) {
            return Collections.emptyList();
        }
        return repository.findAllByStatus(status);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> list(String status, String sortBy, String sortDir) {
        String by = "dueAt"; // by def

        if ("status".equalsIgnoreCase(sortBy)) by = "status";

        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, by);

        if (status == null || status.isBlank()) return repository.findAll(sort);

        Status st;
        try {
            st = Status.valueOf(status.trim()
                    .toUpperCase()
                    .replace(' ', '_')
                    .replace('-', '_')
            );
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown status");
        }
        return repository.findAllByStatus(st, sort);
    }
}

