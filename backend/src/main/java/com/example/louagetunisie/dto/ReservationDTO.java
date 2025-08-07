package com.example.louagetunisie.dto;

public class ReservationDTO {
    private Long trajetId;
    private int nombrePlaces;

    public ReservationDTO(Long trajetId, int nombrePlaces) {
        this.trajetId = trajetId;
        this.nombrePlaces = nombrePlaces;
    }

    public Long getTrajetId() {
        return trajetId;
    }

    public void setTrajetId(Long trajetId) {
        this.trajetId = trajetId;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }
}

