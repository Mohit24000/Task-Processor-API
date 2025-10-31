package com.learnSpringBoot.taskprocessor.aop;

import com.learnSpringBoot.taskprocessor.model.Task;
import com.learnSpringBoot.taskprocessor.service.TaskService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {

    @Autowired
    private TaskService taskService;

    @Around("execution(* com.learnSpringBoot.taskprocessor.service.WorkerService.processTask(..))")
    public Object addRetry(ProceedingJoinPoint pjp) throws Throwable {
        Task task = (Task) pjp.getArgs()[0];
        int retries = 0;
        while (retries < 3) {
            try {
                return pjp.proceed();
            } catch (Exception ex) {
                retries++;
                task.setRetries(retries);
                taskService.updateTask(task.getId(), "RETRY_" + retries);
                System.out.println("Retry " + retries + " for task " + task.getId());
                if (retries == 3) {
                    taskService.updateTask(task.getId(), "FAILED_PERMANENTLY");
                }
                Thread.sleep(1000);
            }
        }
        return null;
    }
}
