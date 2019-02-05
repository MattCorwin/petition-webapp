package com.mattcorwin.petitonwebapp.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    @Size(min=10, max=500)
    private String commentText;

    @ManyToOne
    private Employee employee;

    @NotNull
    private CommentTypes type;

    public Comment() {
    }

    public Comment(LocalDate date, String commentText, Employee employee, CommentTypes type) {
        this.date = date;
        this.commentText = commentText;
        this.employee = employee;
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public CommentTypes getType() {
        return type;
    }

    public void setType(CommentTypes type) {
        this.type = type;
    }
}

