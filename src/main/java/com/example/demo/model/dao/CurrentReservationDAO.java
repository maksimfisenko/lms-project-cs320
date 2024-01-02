package com.example.demo.model.dao;

import com.example.demo.model.entities.CurrentReservation;
import java.util.List;

public interface CurrentReservationDAO {
    void addCurrentReservation(CurrentReservation currentReservation);
    CurrentReservation getCurrentReservationById(int id);
    List<CurrentReservation> getAllCurrentReservations();
    void updateCurrentReservation(CurrentReservation currentReservation);
    void deleteCurrentReservation(int id);
}
