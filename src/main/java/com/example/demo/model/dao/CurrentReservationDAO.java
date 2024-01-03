package com.example.demo.model.dao;

import com.example.demo.model.entities.CurrentReservation;
import java.util.List;

public interface CurrentReservationDAO {
    void addCurrentReservation(CurrentReservation currentReservation);
    CurrentReservation getCurrentReservationById(int id);
    List<CurrentReservation> getAllCurrentReservations();
    List<CurrentReservation> getCurrentReservationsByReaderId(int readerId);
    void updateCurrentReservation(CurrentReservation currentReservation);
    void deleteCurrentReservation(int id);
    List<CurrentReservation> getReservationsWithNullDate();
    List<CurrentReservation> getReservationsWithDate();
    List<CurrentReservation> getCurrentReservationsByReaderIdNotNull(int readerId);
}
