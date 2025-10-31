package com.learnSpringBoot.taskprocessor.model;

public class Task {
    private String id;
    private String payload;
    private String status;
    private int retries;

    public Task(String id, String payload, String status) {
        this.id = id;
        this.payload = payload;
        this.status = status;
        this.retries = 0;
    }

    public String getId() { return id; }
    public String getPayload() { return payload; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getRetries() { return retries; }
    public void setRetries(int retries) { this.retries = retries; }
}
