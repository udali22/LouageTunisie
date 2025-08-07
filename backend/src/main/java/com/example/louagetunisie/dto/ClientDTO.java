package com.example.louagetunisie.dto;

import com.example.louagetunisie.model.Reservation;

import java.util.List;

public class ClientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private List<ReservationDTO> historiqueReservation;
    private Integer cin;

    public ClientDTO(Long id, String nom, String prenom, String email, String telephone, List<ReservationDTO> historiqueReservation, Integer cin) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.historiqueReservation = historiqueReservation;
        this.cin = cin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<ReservationDTO> getHistoriqueReservation() {
        return historiqueReservation;
    }

    public void setHistoriqueReservation(List<ReservationDTO> historiqueReservation) {
        this.historiqueReservation = historiqueReservation;
    }

    public Integer getCin() {
        return cin;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }
}
