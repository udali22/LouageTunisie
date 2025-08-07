package com.example.louagetunisie.controller;

import com.example.louagetunisie.model.Utilisateur;
import com.example.louagetunisie.repository.UtilisateurRepository;
import com.example.louagetunisie.service.UtilisateurService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/utilisateurs")
public class UtilisateurController {
    private UtilisateurRepository utilisateurRepository;
    private UtilisateurService utilisateurService;
    public UtilisateurController(UtilisateurService utilisateurService, UtilisateurRepository utilisateurRepository) {
        this.utilisateurService = utilisateurService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Long count() {
        return utilisateurRepository.countUser();
    }

}
