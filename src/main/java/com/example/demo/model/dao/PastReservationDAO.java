package com.example.demo.model.dao;

import java.util.List;
import  com.example.demo.model.entities.PastReservation ;
public interface PastReservationDAO {
    void addPastReservation(PastReservation pastReservation);
    PastReservation getPastReservationById(int id);
    List<PastReservation> getAllPastReservations();
    void updatePastReservation(PastReservation pastReservation);
    void deletePastReservation(int id);
}
