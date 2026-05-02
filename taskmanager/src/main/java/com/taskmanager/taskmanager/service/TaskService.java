package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.model.*;
import com.taskmanager.taskmanager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProjectRepository projectRepo;

    public Task create(Task task, String email) {

        User loggedUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"ADMIN".equals(loggedUser.getRole())) {
            throw new RuntimeException("Only admin can create tasks");
        }

        if (task.getAssignedTo() == null || task.getAssignedTo().getId() == null) {
            throw new RuntimeException("Assigned user required");
        }

        if (task.getProject() == null || task.getProject().getId() == null) {
            throw new RuntimeException("Project required");
        }

        User user = userRepo.findById(task.getAssignedTo().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepo.findById(task.getProject().getId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        task.setAssignedTo(user);
        task.setProject(project);
        task.setStatus("TODO");

        return taskRepo.save(task);
    }

    public List<Task> getAll() {
        return taskRepo.findAll();
    }

    public Task updateStatus(Long id, String status) {

        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(status);

        return taskRepo.save(task);
    }
}