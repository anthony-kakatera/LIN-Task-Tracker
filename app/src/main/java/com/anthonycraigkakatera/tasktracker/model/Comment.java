package com.anthonycraigkakatera.tasktracker.model;

public class Comment {
    String staffName, date, comment;

    public Comment(String staffName, String date, String comment) {
        this.staffName = staffName;
        this.date = date;
        this.comment = comment;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
