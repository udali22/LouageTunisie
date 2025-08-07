package com.example.louagetunisie.dto;

import java.time.LocalDateTime;

public class TrajetDTO {

    private Long id;

    private String stationDepart;

    private String stationArrivee;

    private LocalDateTime dateDepart;

    private double prix;

    private String nomChauffeur;

    public TrajetDTO() {
    }

    public TrajetDTO(Long id, String stationDepart, String stationArrivee, LocalDateTime dateDepart,
                     double prix, String nomChauffeur) {
        this.id = id;
        this.stationDepart = stationDepart;
        this.stationArrivee = stationArrivee;
        this.dateDepart = dateDepart;
        this.prix = prix;
        this.nomChauffeur = nomChauffeur;

    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationDepart() {
        return stationDepart;
    }

    public void setStationDepart(String stationDepart) {
        this.stationDepart = stationDepart;
    }

    public String getStationArrivee() {
        return stationArrivee;
    }

    public void setStationArrivee(String stationArrivee) {
        this.stationArrivee = stationArrivee;
    }

    public LocalDateTime getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getNomChauffeur() {
        return nomChauffeur;
    }

    public void setNomChauffeur(String nomChauffeur) {
        this.nomChauffeur = nomChauffeur;
    }

}
