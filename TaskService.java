package com.learnSpringBoot.taskprocessor.service;

import com.learnSpringBoot.taskprocessor.model.Task;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

@Service
public class  TaskService {
    private final ConcurrentHashMap<String, Task> store = new ConcurrentHashMap<>();

    public Task createTask(String payload) {
        String id = UUID.randomUUID().toString();
        Task t = new Task(id, payload, "PENDING");
        store.put(id, t);
        return t;
    }

    public Task getTask(String id) {
        return store.get(id);
    }

    public void updateTask(String id, String status) {
        Task t = store.get(id);
        if (t != null) t.setStatus(status);
    }

    public ConcurrentHashMap<String, Task> getAll() {
        return store;
    }
}
