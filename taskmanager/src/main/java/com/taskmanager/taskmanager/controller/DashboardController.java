package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private TaskRepository taskRepo;

    @GetMapping
    public Map<String, Object> stats() {

        Map<String, Object> data = new HashMap<>();

        data.put("totalTasks", taskRepo.count());
        data.put("todo", taskRepo.countByStatus("TODO"));
        data.put("inProgress", taskRepo.countByStatus("IN_PROGRESS"));
        data.put("done", taskRepo.countByStatus("DONE"));
        data.put("overdueTasks", taskRepo.countByDueDateBefore(LocalDate.now()));

        return data;
    }
}