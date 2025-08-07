package com.example.louagetunisie.controller;

import com.example.louagetunisie.dto.ReservationDTO;
import com.example.louagetunisie.dto.ReservationResponseDTO;
import com.example.louagetunisie.model.Reservation;
import com.example.louagetunisie.repository.ReservationRepository;
import com.example.louagetunisie.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
private ReservationService reservationService;
private ReservationRepository reservationRepository;
public ReservationController(ReservationService reservationService, ReservationRepository reservationRepository) {
    this.reservationService = reservationService;
    this.reservationRepository = reservationRepository;
}
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> reserver(@Valid @RequestBody ReservationDTO dto) {
        reservationService.reserverTrajet(dto);
        return ResponseEntity.ok("Réservation enregistrée avec succès !");
    }
    @PostMapping("/{id}/annuler")
    @PreAuthorize("hasRole('CLIENT')")
    public void annuler(@PathVariable Long id) {
    reservationService.annulerReservation(id);
    }
    @PostMapping("/{id}/confirmer")
    @PreAuthorize("hasRole('CHAUFFEUR')")
    public void confirmer(@PathVariable Long id){
    reservationService.confirmReservation(id);
    }
    @GetMapping("/mes-reservations")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ReservationResponseDTO>> mesReservations() {
        List<ReservationResponseDTO> reservations = reservationService.getReservationsPourClientActuel();
        return ResponseEntity.ok(reservations);
    }



}
