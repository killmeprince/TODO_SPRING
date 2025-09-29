package org.example.webtodo.service;

import org.example.webtodo.model.Status;
import org.example.webtodo.model.Task;
import org.example.webtodo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    TaskRepository repository;
    @InjectMocks
    TaskService service;

    @Test
    void create_StatusTODO_DefStatus() {
        Task t = new Task();
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        Task saved = service.create(t);
        assertEquals(Status.TODO, saved.getStatus());
        verify(repository).save(t);
    }

    @Test
    void get_throwIfMissing_Exception() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.get(99L));
    }

    @Test
    void list_withStatusAndSort() {
        when(repository.findAllByStatus(eq(Status.IN_PROGRESS), any())).thenReturn(List.of(new Task()));
        List<Task> result = service.list("in progress", "dueAt", "desc");
        assertEquals(1, result.size());
        verify(repository).findAllByStatus(eq(Status.IN_PROGRESS), any());
    }
}
