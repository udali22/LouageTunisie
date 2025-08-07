package com.example.louagetunisie.dto;

public class ChauffeurDTO {
    private Long id;
    private String permis;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String vehiculeMatricule;
    private Long numLicence;
    private Integer cin; 

    public ChauffeurDTO() {}

    public ChauffeurDTO(Long id, String permis, String nom, String prenom, String email, String telephone, String vehiculeMatricule, Long numLicence, Integer cin) {
        this.id = id;
        this.permis = permis;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.vehiculeMatricule = vehiculeMatricule;
        this.numLicence = numLicence;
        this.cin = cin; // ✅ Initialisation dans le constructeur
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermis() {
        return permis;
    }

    public void setPermis(String permis) {
        this.permis = permis;
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

    public String getVehiculeMatricule() {
        return vehiculeMatricule;
    }

    public void setVehiculeMatricule(String vehiculeMatricule) {
        this.vehiculeMatricule = vehiculeMatricule;
    }

    public Long getNumLicence() {
        return numLicence;
    }

    public void setNumLicence(Long numLicence) {
        this.numLicence = numLicence;
    }

    public Integer getCin() {
        return cin;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }
}
