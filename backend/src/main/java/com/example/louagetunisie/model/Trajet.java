package com.example.louagetunisie.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Trajet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station_depart_id")
    private Station stationDepart;

    @ManyToOne
    @JoinColumn(name = "station_arrivee_id")
    private Station stationArrivee;

    private LocalDateTime dateDepart;

    private double prix;

    @ManyToOne
    @JoinColumn(name = "chauffeur_id")
    private Chauffeur chauffeur;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    @OneToMany(mappedBy = "trajet")
    private List<Reservation> reservations;
    private int nombrePlaces= 8 ;

    public Trajet(List<Reservation> reservations, Vehicule vehicule, Chauffeur chauffeur, double prix, LocalDateTime dateDepart, Station stationArrivee, Station stationDepart, Long id , int nombrePlaces) {
        this.reservations = reservations;
        this.vehicule = vehicule;
        this.chauffeur = chauffeur;
        this.prix = prix;
        this.dateDepart = dateDepart;
        this.stationArrivee = stationArrivee;
        this.stationDepart = stationDepart;
        this.id = id;
        this.nombrePlaces = nombrePlaces;
    }

    public Trajet() {

    }

    public Long getId() {
        return id;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Station getStationDepart() {
        return stationDepart;
    }

    public void setStationDepart(Station stationDepart) {
        this.stationDepart = stationDepart;
    }

    public Station getStationArrivee() {
        return stationArrivee;
    }

    public void setStationArrivee(Station stationArrivee) {
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

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
