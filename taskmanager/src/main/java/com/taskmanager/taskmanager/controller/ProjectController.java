package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.model.Project;
import com.taskmanager.taskmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public Project createProject(@RequestBody Project project,
                                 @RequestParam Long userId) {
        return projectService.createProject(project, userId);
    }

    @GetMapping
    public List<Project> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping("/{projectId}/add-user/{userId}")
    public Project addUserToProject(@PathVariable Long projectId,
                                   @PathVariable Long userId) {
        return projectService.addUserToProject(projectId, userId);
    }

    @DeleteMapping("/{projectId}/remove-user/{userId}")
    public Project removeUserFromProject(@PathVariable Long projectId,
                                         @PathVariable Long userId) {
        return projectService.removeUserFromProject(projectId, userId);
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "Project deleted successfully";
    }
}