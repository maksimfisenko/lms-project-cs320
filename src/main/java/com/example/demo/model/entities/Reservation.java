package com.example.demo.model.entities;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Reservation {

    private int id;
    private Book bookReserved;
    private Reader reader;
    private LocalDate dateOfPicking;
    private int numOfDaysForReservation;

    public Reservation(int id, Book bookReserved, Reader reader, LocalDate dateOfPicking, int numOfDaysForReservation) {
        this.id = id;
        this.bookReserved = bookReserved;
        this.reader = reader;
        this.dateOfPicking = dateOfPicking;
        this.numOfDaysForReservation = numOfDaysForReservation;
    }

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBookReserved() {
        return bookReserved;
    }

    public void setBookReserved(Book bookReserved) {
        this.bookReserved = bookReserved;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public LocalDate getDateOfPicking() {
        return dateOfPicking;
    }

    public void setDateOfPicking(LocalDate dateOfPicking) {
        this.dateOfPicking = dateOfPicking;
    }

    public int getNumOfDaysForReservation() {
        return numOfDaysForReservation;
    }

    public void setNumOfDaysForReservation(int numOfDaysForReservation) {
        this.numOfDaysForReservation = numOfDaysForReservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && numOfDaysForReservation == that.numOfDaysForReservation && Objects.equals(bookReserved, that.bookReserved) && Objects.equals(reader, that.reader) && Objects.equals(dateOfPicking, that.dateOfPicking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookReserved, reader, dateOfPicking, numOfDaysForReservation);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", bookReserved=" + bookReserved +
                ", reader=" + reader +
                ", dateOfPicking=" + dateOfPicking +
                ", numOfDaysForReservation=" + numOfDaysForReservation +
                '}';
    }

    public char getReservationId() {
        return 0;
    }
}
