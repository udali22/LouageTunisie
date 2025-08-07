package com.example.louagetunisie.controller;

import com.example.louagetunisie.model.Utilisateur;
import com.example.louagetunisie.service.SessionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {
    private final SessionService sessionService;
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }
    @GetMapping("/clients-actifs/count")
    @PreAuthorize("hasRole('ADMIN')")
    public long countActiveClients() {
        return sessionService.countActiveClients();
    }
    @GetMapping("/chauffeurs-actifs/count")
    @PreAuthorize("hasRole('ADMIN')")
    public long countActiveChauffeur() {
        return sessionService.countActiveChauffeurs();
    }
    @GetMapping("/clients-actifs")
    @PreAuthorize("hasRole('ADMIN')")
    List<Utilisateur> getActiveClients() {
        return sessionService.getActiveClients();
    }
    @GetMapping("/chauffeurs-actifs")
    @PreAuthorize("hasRole('ADMIN')")
    List<Utilisateur> getActiveChauffeurs() {
        return sessionService.getActiveChauffeurs();
    }

}
