package com.example.demo.model;

public class UserSearchModel {

    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private int numOfStrikes;
    private boolean banStatus;

    public UserSearchModel(String firstName, String lastName, String login,
                           String password, int numOfStrikes){
        this.firstName = firstName;
        this.lastName= lastName;
        this.login = login;
        this.numOfStrikes = numOfStrikes;
        this.password = password;
        this.banStatus = numOfStrikes >= 3;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getNumOfStrikes() {
        return numOfStrikes;
    }

    public boolean isBanStatus() {
        return banStatus;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNumOfStrikes(int numOfStrikes) {
        this.numOfStrikes = numOfStrikes;
    }

    public void setBanStatus(boolean banStatus) {
        this.banStatus = banStatus;
    }
}
