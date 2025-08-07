package com.example.louagetunisie.service;

import com.example.louagetunisie.dto.ReservationDTO;
import com.example.louagetunisie.dto.ReservationResponseDTO;
import com.example.louagetunisie.model.*;
import com.example.louagetunisie.repository.ChauffeurRepository;
import com.example.louagetunisie.repository.ClientRepository;
import com.example.louagetunisie.repository.ReservationRepository;
import com.example.louagetunisie.repository.TrajetRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final TrajetRepository trajetRepository;
    private final ChauffeurRepository chauffeurRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ClientRepository clientRepository,
                              TrajetRepository trajetRepository, ChauffeurRepository chauffeurRepository) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.trajetRepository = trajetRepository;
        this.chauffeurRepository = chauffeurRepository;
    }

    public void reserverTrajet(ReservationDTO dto) {
        Utilisateur authUser = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = authUser.getEmail();
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        Trajet trajet = trajetRepository.findById(dto.getTrajetId())
                .orElseThrow(() -> new RuntimeException("Trajet non trouvé"));

        // Vérifier si l'utilisateur a déjà réservé ce trajet
        boolean dejaReserve = reservationRepository.existsByClientAndTrajet(client, trajet);
        if (dejaReserve) {
            throw new RuntimeException("Vous avez déjà réservé ce trajet.");
        }

        int placesDemandees = dto.getNombrePlaces();
        int placesDisponibles = trajet.getNombrePlaces(); // nombre de places restantes

        // Vérifier s'il reste assez de places
        if (placesDemandees > placesDisponibles) {
            throw new RuntimeException("Pas assez de places disponibles. Places restantes : " + placesDisponibles);
        }

        // Créer et enregistrer la réservation
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setTrajet(trajet);
        reservation.setNombrePlaces(placesDemandees);
        reservation.setEtat("EN_ATTENTE");
        reservation.setDateReservation(LocalDateTime.now());

        reservationRepository.save(reservation);

        // Mettre à jour les places restantes
        trajet.setNombrePlaces(placesDisponibles - placesDemandees);
        trajetRepository.save(trajet);
    }
    public void annulerReservation(Long reservationId) {
        Utilisateur authUser = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = authUser.getEmail();
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        // Chercher la réservation
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        // Vérifier que la réservation appartient au client connecté
        if (!reservation.getClient().getId().equals(client.getId())) {
            throw new RuntimeException("Vous ne pouvez pas annuler cette réservation.");
        }

        // Récupérer les infos du trajet
        Trajet trajet = reservation.getTrajet();

        // Restaurer les places dans le trajet
        int placesAnnulees = reservation.getNombrePlaces();
        trajet.setNombrePlaces(trajet.getNombrePlaces() + placesAnnulees);
        trajetRepository.save(trajet);

        reservation.setEtat("ANNULEE");
        reservationRepository.save(reservation);

    }
    public void confirmReservation(Long reservationId) {
        Utilisateur authUser = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = authUser.getEmail();
        Chauffeur chauffeur = chauffeurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        // Chercher la réservation
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
        if (!reservation.getTrajet().getChauffeur().getId() .equals(chauffeur.getId())) {
            throw new RuntimeException("Vous ne pouvez pas confirmer cette réservation.");
        }
        if (reservation.getEtat().equals("ANNULEE")) {
            throw new RuntimeException("La réservation a été annulée par le client ");
        }
        reservation.setEtat("CONFIRMEE");
        reservationRepository.save(reservation);
    }
    public List<ReservationResponseDTO> getReservationsPourClientActuel() {
        Utilisateur authUser = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = authUser.getEmail();

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        return client.getHistoriqueReservation().stream()
                .map(ReservationResponseDTO::new)
                .collect(Collectors.toList());
    }



}
