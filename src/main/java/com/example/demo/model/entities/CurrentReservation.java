package com.example.demo.model.entities;

import java.time.LocalDate;

public class CurrentReservation extends Reservation {
    public CurrentReservation(int id, Book bookReserved, Reader reader, LocalDate dateOfPicking, int numOfDaysForReservation) {
        super(id, bookReserved, reader, dateOfPicking, numOfDaysForReservation);
    }
}
