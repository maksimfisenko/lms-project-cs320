package com.example.demo.model.entities;

import java.time.LocalDate;
import java.util.Objects;

public class PastReservation extends Reservation {

    private LocalDate dateOfReturn;
    private boolean strikeIsIssued;
    private String strikeDescription;
    private String bookConditionOnReturn;

    public PastReservation(int id, Book bookReserved, Reader reader, LocalDate dateOfPicking, int numOfDaysForReservation, LocalDate dateOfReturn, boolean strikeIsIssued, String strikeDescription, String bookConditionOnReturn) {
        super(id, bookReserved, reader, dateOfPicking, numOfDaysForReservation);
        this.dateOfReturn = dateOfReturn;
        this.strikeIsIssued = strikeIsIssued;
        this.strikeDescription = strikeDescription;
        this.bookConditionOnReturn = bookConditionOnReturn;
    }

    public PastReservation() {
    }

    public LocalDate getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(LocalDate dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public boolean isStrikeIsIssued() {
        return strikeIsIssued;
    }

    public void setStrikeIsIssued(boolean strikeIsIssued) {
        this.strikeIsIssued = strikeIsIssued;
    }

    public String getStrikeDescription() {
        return strikeDescription;
    }

    public void setStrikeDescription(String strikeDescription) {
        this.strikeDescription = strikeDescription;
    }

    public String getBookConditionOnReturn() {
        return bookConditionOnReturn;
    }

    public void setBookConditionOnReturn(String bookConditionOnReturn) {
        this.bookConditionOnReturn = bookConditionOnReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PastReservation that = (PastReservation) o;
        return strikeIsIssued == that.strikeIsIssued && Objects.equals(dateOfReturn, that.dateOfReturn) && Objects.equals(strikeDescription, that.strikeDescription) && Objects.equals(bookConditionOnReturn, that.bookConditionOnReturn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateOfReturn, strikeIsIssued, strikeDescription, bookConditionOnReturn);
    }

    @Override
    public String toString() {
        return "PastReservation{" +
                "dateOfReturn=" + dateOfReturn +
                ", strikeIsIssued=" + strikeIsIssued +
                ", strikeDescription='" + strikeDescription + '\'' +
                ", bookConditionOnReturn='" + bookConditionOnReturn + '\'' +
                '}';
    }
}
