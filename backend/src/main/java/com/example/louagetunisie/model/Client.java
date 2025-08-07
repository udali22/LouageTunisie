package com.example.louagetunisie.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Client extends Utilisateur {

    @OneToMany(mappedBy = "client")
    private List<Reservation> historiqueReservation;

    public Client(List<Reservation> historiqueReservation) {
        this.historiqueReservation = historiqueReservation;
    }
    public Client() {}

    public List<Reservation> getHistoriqueReservation() {
        return historiqueReservation;
    }

    public void setHistoriqueReservation(List<Reservation> historiqueReservation) {
        this.historiqueReservation = historiqueReservation;
    }
}
