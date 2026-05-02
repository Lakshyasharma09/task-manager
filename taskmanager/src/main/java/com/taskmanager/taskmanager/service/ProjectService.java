package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.model.Project;
import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.repository.ProjectRepository;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    public Project createProject(Project project, Long creatorId) {

        if (creatorId == null) {
            throw new RuntimeException("Creator id required");
        }

        User creator = userRepo.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> members = new ArrayList<>();
        members.add(creator);

        project.setMembers(members);

        return projectRepo.save(project);
    }

    public List<Project> getProjects() {
        return projectRepo.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Project addUserToProject(Long projectId, Long userId) {

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (project.getMembers() == null) {
            project.setMembers(new ArrayList<>());
        }

        boolean exists = project.getMembers()
                .stream()
                .anyMatch(u -> u.getId().equals(userId));

        if (!exists) {
            project.getMembers().add(user);
        }

        return projectRepo.save(project);
    }

    public Project removeUserFromProject(Long projectId, Long userId) {

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (project.getMembers() != null) {
            project.getMembers().removeIf(u -> u.getId().equals(userId));
        }

        return projectRepo.save(project);
    }

    public void deleteProject(Long id) {
        projectRepo.deleteById(id);
    }
}