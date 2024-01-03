package com.example.demo.model;

import java.time.LocalDate;

public class UserPastReservationsModel {
    private int id;
    private LocalDate datePicked;
    private LocalDate dateReturned;
    private String bookTitle;
    private boolean strikeIssued;
    private String strikeDescription;

    public UserPastReservationsModel(int id, LocalDate datePicked, LocalDate dateReturned, String bookTitle, boolean strikeIssued, String strikeDescription) {
        this.id = id;
        this.datePicked = datePicked;
        this.dateReturned = dateReturned;
        this.bookTitle = bookTitle;
        this.strikeIssued = strikeIssued;
        this.strikeDescription = strikeDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDatePicked() {
        return datePicked;
    }

    public void setDatePicked(LocalDate datePicked) {
        this.datePicked = datePicked;
    }

    public LocalDate getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(LocalDate dateReturned) {
        this.dateReturned = dateReturned;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public boolean isStrikeIssued() {
        return strikeIssued;
    }

    public void setStrikeIssued(boolean strikeIssued) {
        this.strikeIssued = strikeIssued;
    }

    public String getStrikeDescription() {
        return strikeDescription;
    }

    public void setStrikeDescription(String strikeDescription) {
        this.strikeDescription = strikeDescription;
    }
}
