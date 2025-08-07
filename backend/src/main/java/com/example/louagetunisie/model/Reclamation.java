package com.example.louagetunisie.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private String message;
    private String etat;
    private LocalDateTime date;

    // Getters et Setters (à ajouter si tu veux)
}
