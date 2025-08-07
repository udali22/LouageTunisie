package com.example.louagetunisie.service;

import com.example.louagetunisie.dto.TrajetDTO;
import com.example.louagetunisie.model.*;
import com.example.louagetunisie.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrajetService {

    private final TrajetRepository trajetRepository;
    private final ChauffeurRepository chauffeurRepository;
    private final VehiculeRepository vehiculeRepository;
    private final StationRepository stationRepository;

    public TrajetService(
            TrajetRepository trajetRepository,
            ChauffeurRepository chauffeurRepository,
            VehiculeRepository vehiculeRepository,
            StationRepository stationRepository) {
        this.trajetRepository = trajetRepository;
        this.chauffeurRepository = chauffeurRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.stationRepository = stationRepository;
    }

    // Rechercher les trajets par station et date
    public List<Trajet> rechercherParStationsEtDate(Long departId, Long arriveeId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);
        return trajetRepository.findByStationDepartIdAndStationArriveeIdAndDateDepartBetween(departId, arriveeId, start, end);
    }

    //  Ajouter un trajet (appelé par chauffeur)
    public Trajet ajouterTrajetParChauffeur(Trajet trajet) {
        Utilisateur authUser = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = authUser.getEmail();
        Chauffeur chauffeur = chauffeurRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Chauffeur introuvable"));

        Station stationDepart = stationRepository.findById(trajet.getStationDepart().getId())
                .orElseThrow(() -> new IllegalArgumentException("Station de départ introuvable"));

        Station stationArrivee = stationRepository.findById(trajet.getStationArrivee().getId())
                .orElseThrow(() -> new IllegalArgumentException("Station d'arrivée introuvable"));

        trajet.setChauffeur(chauffeur);
        trajet.setStationDepart(stationDepart);
        trajet.setStationArrivee(stationArrivee);
        trajet.setVehicule(chauffeur.getVehicule());

        return trajetRepository.save(trajet);
    }

    //API pour modifier Trajet
    public Trajet modifierTrajet(Long trajetId, Trajet nouveauTrajet) {
        Trajet trajetExistant = trajetRepository.findById(trajetId)
                .orElseThrow(() -> new IllegalArgumentException("Trajet introuvable avec id: " + trajetId));

        trajetExistant.setDateDepart(nouveauTrajet.getDateDepart());
        trajetExistant.setPrix(nouveauTrajet.getPrix());


        Station depart = stationRepository.findById(nouveauTrajet.getStationDepart().getId())
                .orElseThrow(() -> new IllegalArgumentException("Station de départ invalide"));

        Station arrivee = stationRepository.findById(nouveauTrajet.getStationArrivee().getId())
                .orElseThrow(() -> new IllegalArgumentException("Station d'arrivée invalide"));

        Chauffeur chauffeur = chauffeurRepository.findById(nouveauTrajet.getChauffeur().getId())
                .orElseThrow(() -> new IllegalArgumentException("Chauffeur invalide"));

        trajetExistant.setStationDepart(depart);
        trajetExistant.setStationArrivee(arrivee);
        trajetExistant.setChauffeur(chauffeur);
        trajetExistant.setVehicule(chauffeur.getVehicule());

        return trajetRepository.save(trajetExistant);
    }
    public void supprimerTrajet(Long trajetId) {
        if (!trajetRepository.existsById(trajetId)) {
            throw new IllegalArgumentException("Trajet introuvable avec id: " + trajetId);
        }
        trajetRepository.deleteById(trajetId);
    }
    public List<TrajetDTO> getAllTrajets() {
        return trajetRepository.findAll()
                .stream()
                .map(trajet -> new TrajetDTO(
                        trajet.getId(),
                        trajet.getStationDepart().getNom(),
                        trajet.getStationArrivee().getNom(),
                        trajet.getDateDepart(),
                        trajet.getPrix(),
                        trajet.getChauffeur().getNom() + " " + trajet.getChauffeur().getPrenom()
                ))
                .collect(Collectors.toList());
    }
    public long countTrajetsAjoutesAujourdHui() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);
        return trajetRepository.countByDateDepartBetween(startOfDay, endOfDay);
    }
}
