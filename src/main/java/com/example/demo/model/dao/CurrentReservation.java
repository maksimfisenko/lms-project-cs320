package com.example.demo.model.dao;

import java.util.List;

public interface CurrentReservation {
    void addCurrentReservation(CurrentReservation currentReservation);
    CurrentReservation getCurrentReservationById(int id);
    List<CurrentReservation> getAllCurrentReservations();
    void updateCurrentReservation(CurrentReservation currentReservation);
    void deleteCurrentReservation(int id);
}
