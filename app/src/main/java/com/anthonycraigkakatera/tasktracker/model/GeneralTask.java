package com.anthonycraigkakatera.tasktracker.model;

import java.util.List;

public class GeneralTask {
    private String title;
    private String dueDate;
    private String description;
    private String status;
    private String id;
    private List<String> comment;

    public GeneralTask(String title, String dueDate, String description, String status, List<String> comment) {
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        this.status = status;
        this.comment = comment;
    }

    public GeneralTask(String title, String dueDate, String description, String status) {
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        this.status = status;
    }

    public GeneralTask(String title, String dueDate, String description, String status, String id) {
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }
}
