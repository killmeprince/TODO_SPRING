package org.example.webtodo.service;

import org.example.webtodo.model.Status;
import org.example.webtodo.model.Task;

import java.util.List;

public interface Taskable {
    Task create(Task task);
    Task update(Long id, Task task);
    void delete(Long id);
    Task get(Long id);
    List<?> list();
    List<?> list(Status status);
    List<Task> list(String status, String sortBy, String sortDir);

}
