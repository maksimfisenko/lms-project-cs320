package com.ozyegin.lms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reader extends User{

    private int numOfStrikes;
    private List<Reservation> reservations;

    public Reader(int id, String login, String password, String name, String surname) {
        super(id, login, password, name, surname);
        this.numOfStrikes = 0;
        this.reservations = new ArrayList<>();
    }

    public int getNumOfStrikes() {
        return numOfStrikes;
    }

    public void setNumOfStrikes(int numOfStrikes) {
        this.numOfStrikes = numOfStrikes;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Reader reader = (Reader) o;
        return numOfStrikes == reader.numOfStrikes && Objects.equals(reservations, reader.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numOfStrikes, reservations);
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + this.getId() +
                ", login='" + this.getLogin() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", surname='" + this.getSurname() + '\'' +
                ", numOfStrikes=" + this.getNumOfStrikes() + '\'' +
                ", reservations=" + this.getReservations() + '\'' +
                '}';
    }
}
