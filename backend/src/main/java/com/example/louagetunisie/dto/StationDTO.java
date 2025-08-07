package com.example.louagetunisie.dto;

public class StationDTO {
    private Long id;
    private String nom;
    private String ville;
    private String adresse;

    public StationDTO(Long id, String nom, String ville, String adresse) {
        this.id = id;
        this.nom = nom;
        this.ville = ville;
        this.adresse = adresse;
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

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
