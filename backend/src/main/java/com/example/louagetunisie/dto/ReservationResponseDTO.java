package com.example.louagetunisie.dto;

import com.example.louagetunisie.model.Reservation;

import java.time.LocalDateTime;

public class ReservationResponseDTO {
    private Long id;
    private String etat;
    private int nombrePlaces;
    private LocalDateTime dateReservation;
    private Long trajetId;
    private String trajetDepart;
    private String trajetDestination;

    // Constructeur depuis l'entité
    public ReservationResponseDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.etat = reservation.getEtat();
        this.nombrePlaces = reservation.getNombrePlaces();
        this.dateReservation = reservation.getDateReservation();
        this.trajetId = reservation.getTrajet().getId();
        this.trajetDepart = String.valueOf(reservation.getTrajet().getStationDepart().getVille());
        this.trajetDestination = String.valueOf(reservation.getTrajet().getStationArrivee().getVille());
    }

    public ReservationResponseDTO(Long id, String etat, int nombrePlaces, LocalDateTime dateReservation, Long trajetId, String trajetDepart, String trajetDestination) {
        this.id = id;
        this.etat = etat;
        this.nombrePlaces = nombrePlaces;
        this.dateReservation = dateReservation;
        this.trajetId = trajetId;
        this.trajetDepart = trajetDepart;
        this.trajetDestination = trajetDestination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Long getTrajetId() {
        return trajetId;
    }

    public void setTrajetId(Long trajetId) {
        this.trajetId = trajetId;
    }

    public String getTrajetDepart() {
        return trajetDepart;
    }

    public void setTrajetDepart(String trajetDepart) {
        this.trajetDepart = trajetDepart;
    }

    public String getTrajetDestination() {
        return trajetDestination;
    }

    public void setTrajetDestination(String trajetDestination) {
        this.trajetDestination = trajetDestination;
    }
}

