package com.example.louagetunisie.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Chauffeur extends Utilisateur {

    private String permis;
    private Long numLicence;

    @OneToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;


    @OneToMany(mappedBy = "chauffeur")
    private List<Trajet> listeTrajet;


    public Chauffeur() {}

    public Chauffeur(String permis, Long numLicence, Vehicule vehicule,  List<Trajet> listeTrajet) {
        this.permis = permis;
        this.numLicence = numLicence;
        this.vehicule = vehicule;
        this.listeTrajet = listeTrajet;
    }

    public Long getNumLicence() {
        return numLicence;
    }

    public void setNumLicence(Long numLicence) {
        this.numLicence = numLicence;
    }

    public String getPermis() {
        return permis;
    }

    public void setPermis(String permis) {
        this.permis = permis;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public List<Trajet> getListeTrajet() {
        return listeTrajet;
    }

    public void setListeTrajet(List<Trajet> listeTrajet) {
        this.listeTrajet = listeTrajet;
    }

}
