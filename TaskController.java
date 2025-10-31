package com.learnSpringBoot.taskprocessor.controller;

import com.learnSpringBoot.taskprocessor.model.Task;
import com.learnSpringBoot.taskprocessor.service.TaskService;
import com.learnSpringBoot.taskprocessor.service.WorkerService;
import com.learnSpringBoot.taskprocessor.exception.TaskNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final WorkerService workerService;

    public TaskController(TaskService taskService, WorkerService workerService) {
        this.taskService = taskService;
        this.workerService = workerService;
    }

    @PostMapping
    public ResponseEntity<?> submitTask(@RequestBody Map<String, String> body) throws InterruptedException {
        String payload = body.getOrDefault("payload", "no-payload");
        Task t = taskService.createTask(payload);
        workerService.processTask(t); // async call
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Map.of("taskId", t.getId(), "status", t.getStatus()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStatus(@PathVariable String id) {
        Task t = taskService.getTask(id);
        if (t == null) throw new TaskNotFoundException("Task not found: " + id);
        return ResponseEntity.ok(Map.of(
                "id", t.getId(),
                "status", t.getStatus(),
                "retries", t.getRetries()
        ));
    }

    @GetMapping
    public ResponseEntity<?> listTasks() {
        return ResponseEntity.ok(taskService.getAll().values());
    }
}
