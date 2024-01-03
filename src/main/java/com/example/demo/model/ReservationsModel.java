package com.example.demo.model;

import java.time.LocalDate;

public class ReservationsModel {
    private int id;
    private String userLogin;
    private LocalDate datePicked;
    private String bookTitle;
    private String bookCondition;
    private LocalDate deadline;

    public ReservationsModel(int id, String userLogin, LocalDate datePicked, String bookTitle, String bookCondition) {
        this.id = id;
        this.userLogin = userLogin;
        this.datePicked = datePicked;
        this.bookTitle = bookTitle;
        this.bookCondition = bookCondition;
        this.deadline = datePicked.plusDays(14);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public LocalDate getDatePicked() {
        return datePicked;
    }

    public void setDatePicked(LocalDate datePicked) {
        this.datePicked = datePicked;
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

    public LocalDate getDeadline() {
        return deadline;
    }
}
