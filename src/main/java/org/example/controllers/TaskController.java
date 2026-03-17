package org.example.controllers;

import org.example.model.Task;
import org.example.repository.TaskRepository;
import org.example.services.TaskService;
import org.example.utils.TaskDataLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskDataLoader taskDataLoader;

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public TaskController(TaskDataLoader taskDataLoader, TaskService taskService, TaskRepository taskRepository) {
        this.taskDataLoader = taskDataLoader;
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    // Add Task
    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task newTask = taskService.addTask(task);
        taskDataLoader.exportTasksToJson();
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    // Get All Tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id,
                                        @RequestBody Task updatedTask) {

        Optional<Task> existingTask = taskService.getTaskById(id);

        if (existingTask.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Task not found"));
        }

        Task task = existingTask.get();

        // ✅ Update all fields
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.isStatus());

        taskRepository.save(task);
        taskDataLoader.exportTasksToJson();

        return ResponseEntity.ok(task);
    }


    // Delete Task
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        if (!deleted) {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
    }
}