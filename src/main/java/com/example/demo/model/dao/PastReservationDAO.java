package com.example.demo.model.dao;

import java.util.List;

public interface PastReservationDAO {
    void addPastReservation(PastReservationDAO pastReservationDAO);
    PastReservationDAO getPastReservationById(int id);
    List<PastReservationDAO> getAllPastReservations();
    void updatePastReservation(PastReservationDAO pastReservationDAO);
    void deletePastReservation(int id);
}
