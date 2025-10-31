package com.learnSpringBoot.taskprocessor.service;

import com.learnSpringBoot.taskprocessor.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class WorkerService {

    @Autowired
    private TaskService taskService;

    @Async("taskExecutor")
    public void processTask(Task task) throws InterruptedException {
        try {
            System.out.println("Processing task: " + task.getId());
            taskService.updateTask(task.getId(), "RUNNING");

            Thread.sleep(3000); // simulate long work

            if (Math.random() < 0.5) { // randomly fail to test retry AOP
                throw new RuntimeException("Random failure!");
            }

            taskService.updateTask(task.getId(), "COMPLETED");
            System.out.println("Task " + task.getId() + " done!");

        } catch (Exception e) {
            taskService.updateTask(task.getId(), "FAILED");
            throw e; // let AOP handle retry
        }
    }
}
