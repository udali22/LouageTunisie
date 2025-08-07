package com.example.louagetunisie.dto;

public class LoginResponse {
    private String token;
    private String email;
    private String nom;
    private String prenom;
    private String role;
    private Long id;

    public LoginResponse(String token, String email, String nom, String prenom, String role, Long id) {
        this.token = token;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.id = id;
    }

    // Getters et Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}