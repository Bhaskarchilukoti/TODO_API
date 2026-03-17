package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.example.model.Task;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskDataLoader {

    private final TaskRepository taskRepository;  // <-- must be final

    // Constructor injection ensures Spring injects TaskRepository
    public TaskDataLoader(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // support LocalDateTime

        InputStream is = getClass().getResourceAsStream("/tasks.json");

        if (is == null) {
            System.out.println("tasks.json not found. Skipping initial load.");
            return;
        }

        try {
            List<Task> tasks = mapper.readValue(is, new TypeReference<List<Task>>() {});

            // Only save if repository is empty
            if (taskRepository.count() == 0) {
                taskRepository.saveAll(tasks);
                System.out.println("Loaded " + tasks.size() + " tasks from tasks.json");
            } else {
                System.out.println("Tasks already exist in DB. Skipping JSON load.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tasks from tasks.json", e);
        }
    }
    public static void saveToJson(List<Task> tasks) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // support LocalDateTime

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("tasks.json"), tasks);
            System.out.println("Saved " + tasks.size() + " tasks to tasks.json");
        } catch (IOException e) {
            System.out.println("Failed to save tasks to tasks.json: " + e.getMessage());
        }
    }
    public void exportTasksToJson() {
        List<Task> tasks = taskRepository.findAll();
        saveToJson(tasks);
    }
}