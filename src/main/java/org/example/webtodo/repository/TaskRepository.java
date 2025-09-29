package org.example.webtodo.repository;

import org.example.webtodo.model.Status;
import org.example.webtodo.model.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByStatus(Status status);
    List<Task> findAllByStatus(Status status, Sort sort);

}
