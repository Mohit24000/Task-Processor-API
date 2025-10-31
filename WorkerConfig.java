package com.learnSpringBoot.taskprocessor.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.learnSpringBoot.taskprocessor.service.WorkerService;

@Configuration
public class WorkerConfig {

    @Bean
    @ConditionalOnProperty(name = "worker.enabled", havingValue = "true")
    public WorkerService workerService() {
        return new WorkerService();
    }
}
