package com.example.demo.model;

public class NewReservationModel {
    private int id;
    private String userLogin;
    private int numOfStrikes;
    private String bookTitle;
    private String bookCondition;

    public NewReservationModel(int id, String userLogin, int numOfStrikes, String bookTitle, String bookCondition) {
        this.id = id;
        this.userLogin = userLogin;
        this.numOfStrikes = numOfStrikes;
        this.bookTitle = bookTitle;
        this.bookCondition = bookCondition;
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

    public int getNumOfStrikes() {
        return numOfStrikes;
    }

    public void setNumOfStrikes(int numOfStrikes) {
        this.numOfStrikes = numOfStrikes;
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
}
