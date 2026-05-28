package com.example.webapp.controller;

import com.example.webapp.model.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    // In-memory storage (perfect for a learning project)
    private final List<Task> tasks = new ArrayList<>();

    public TaskController() {
        // Pre-populate with sample tasks
        tasks.add(new Task("Learn Docker", "Containerize the Java app and run it on EC2", "HIGH"));
        tasks.add(new Task("Set up GitHub repo", "Push the project and learn Git workflow", "HIGH"));
        tasks.add(new Task("Explore Spring Boot", "Understand controllers, models, and templates", "MEDIUM"));
        tasks.get(1).setCompleted(true);
    }

    // Home page — shows all tasks
    @GetMapping("/")
    public String index(Model model) {
        long completed = tasks.stream().filter(Task::isCompleted).count();
        model.addAttribute("tasks", tasks);
        model.addAttribute("totalCount", tasks.size());
        model.addAttribute("completedCount", completed);
        model.addAttribute("pendingCount", tasks.size() - completed);
        return "index";
    }

    // Add a new task
    @PostMapping("/tasks/add")
    public String addTask(@RequestParam String title,
                          @RequestParam String description,
                          @RequestParam String priority,
                          RedirectAttributes redirectAttrs) {
        if (title != null && !title.isBlank()) {
            tasks.add(new Task(title.trim(), description.trim(), priority));
            redirectAttrs.addFlashAttribute("successMsg", "Task \"" + title + "\" added!");
        }
        return "redirect:/";
    }

    // Toggle complete/incomplete
    @PostMapping("/tasks/{id}/toggle")
    public String toggleTask(@PathVariable int id, RedirectAttributes redirectAttrs) {
        tasks.stream()
             .filter(t -> t.getId() == id)
             .findFirst()
             .ifPresent(t -> t.setCompleted(!t.isCompleted()));
        return "redirect:/";
    }

    // Delete a task
    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable int id, RedirectAttributes redirectAttrs) {
        tasks.removeIf(t -> t.getId() == id);
        redirectAttrs.addFlashAttribute("successMsg", "Task deleted.");
        return "redirect:/";
    }

    // API endpoint — returns task count as JSON (bonus learning!)
    @GetMapping("/api/stats")
    @ResponseBody
    public String stats() {
        long done = tasks.stream().filter(Task::isCompleted).count();
        return String.format(
            "{\"total\": %d, \"completed\": %d, \"pending\": %d}",
            tasks.size(), done, tasks.size() - done
        );
    }
}
