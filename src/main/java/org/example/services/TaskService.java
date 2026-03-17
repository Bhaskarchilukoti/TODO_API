package org.example.services;

import org.example.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task addTask(Task task);
    List<Task> getAllTasks ();
//    boolean updateTask(Long id, Task newTask);

    boolean deleteTask(Long id);

    Optional<Task> getTaskById(Long id);
}
