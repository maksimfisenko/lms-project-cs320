package com.example.demo.model.dao;

import java.util.List;

public interface PastReservation {
    void addPastReservation(PastReservation pastReservation);
    PastReservation getPastReservationById(int id);
    List<PastReservation> getAllPastReservations();
    void updatePastReservation(PastReservation pastReservation);
    void deletePastReservation(int id);
}
