package com.example.webapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static int counter = 1;

    private int id;
    private String title;
    private String description;
    private boolean completed;
    private String createdAt;
    private String priority; // LOW, MEDIUM, HIGH

    public Task(String title, String description, String priority) {
        this.id = counter++;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = false;
        this.createdAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));
    }

    // Getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public String getCreatedAt() { return createdAt; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
}
