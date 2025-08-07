package com.example.louagetunisie.dto;

import com.example.louagetunisie.model.Vehicule;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String motDePasse;

    @NotBlank(message = "Le rôle est obligatoire")
    private String role; // "CLIENT", "CHAUFFEUR"

    @NotNull(message = "Le numéro de téléphone est obligatoire")
    private int telephone;

    @NotNull(message = "Le CIN est obligatoire")
    private int cin;

    // Champs spécifiques pour chauffeur
    private String permis;
    @Column(unique = true)
    private String matricule;
    @Column(unique = true)
    private Long numLicence;

    // Constructeurs
    public RegisterRequest() {}

    public Long getNumLicence() {
        return numLicence;
    }

    public void setNumLicence(Long numLicence) {
        this.numLicence = numLicence;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getTelephone() { return telephone; }
    public void setTelephone(int telephone) { this.telephone = telephone; }

    public int getCin() { return cin; }
    public void setCin(int cin) { this.cin = cin; }

    public String getPermis() { return permis; }
    public void setPermis(String permis) { this.permis = permis; }
}
