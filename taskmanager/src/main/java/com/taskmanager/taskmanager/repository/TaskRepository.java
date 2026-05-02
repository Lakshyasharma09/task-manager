package com.taskmanager.taskmanager.repository;

import com.taskmanager.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Long> {

    long countByStatus(String status);

    long countByAssignedToId(Long userId);

    long countByDueDateBefore(LocalDate date);
}