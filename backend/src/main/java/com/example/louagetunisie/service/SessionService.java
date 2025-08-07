package com.example.louagetunisie.service;

import com.example.louagetunisie.model.Utilisateur;
import com.example.louagetunisie.repository.SessionRepository;
import com.example.louagetunisie.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UtilisateurRepository utilisateurRepository;

    public SessionService(SessionRepository sessionRepository, UtilisateurRepository utilisateurRepository) {
        this.sessionRepository = sessionRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public long countActiveClients() {
        List<Long> activeUserIds = sessionRepository.findActiveUserIds();

        return utilisateurRepository.findAllById(activeUserIds).stream()
                .filter(user -> "CLIENT".equalsIgnoreCase(user.getRole()))
                .count();
    }

    public List<Utilisateur> getActiveClients() {
        List<Long> activeUserIds = sessionRepository.findActiveUserIds();

        return utilisateurRepository.findAllById(activeUserIds).stream()
                .filter(user -> "CLIENT".equalsIgnoreCase(user.getRole()))
                .toList();
    }
    public long countActiveChauffeurs() {
        List<Long> activeUserIds = sessionRepository.findActiveUserIds();

        return utilisateurRepository.findAllById(activeUserIds).stream()
                .filter(user -> "CHAUFFEUR".equalsIgnoreCase(user.getRole()))
                .count();
    }
    public List<Utilisateur> getActiveChauffeurs() {
        List<Long> activeUserIds = sessionRepository.findActiveUserIds();

        return utilisateurRepository.findAllById(activeUserIds).stream()
                .filter(user -> "CHAUFFEUR".equalsIgnoreCase(user.getRole()))
                .toList();
    }
}

