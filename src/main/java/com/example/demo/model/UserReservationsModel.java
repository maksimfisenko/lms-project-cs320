package com.example.demo.model;

import java.time.LocalDate;

public class UserReservationsModel {
    private int id;
    private String bookTitle;
    private String bookCondition;
    private LocalDate datePicked;
    private LocalDate deadline;

    public UserReservationsModel(int id, String bookTitle, String bookCondition, LocalDate datePicked, LocalDate deadline) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.bookCondition = bookCondition;
        this.datePicked = datePicked;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    public LocalDate getDatePicked() {
        return datePicked;
    }

    public void setDatePicked(LocalDate datePicked) {
        this.datePicked = datePicked;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
