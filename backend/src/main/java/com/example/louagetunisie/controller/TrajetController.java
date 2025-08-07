package com.example.louagetunisie.controller;

import com.example.louagetunisie.dto.TrajetDTO;
import com.example.louagetunisie.model.Trajet;
import com.example.louagetunisie.service.TrajetService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trajets")
public class TrajetController {

    private final TrajetService trajetService;

    public TrajetController(TrajetService trajetService) {
        this.trajetService = trajetService;
    }

    @GetMapping("/search")
    public List<Trajet> search(
            @RequestParam Long depart,
            @RequestParam Long arrivee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return trajetService.rechercherParStationsEtDate(depart, arrivee, date);
    }

    @PostMapping
    @PreAuthorize("hasRole('CHAUFFEUR')")
    public ResponseEntity<?> ajouterTrajetParChauffeur(
            @RequestBody Trajet trajet) {
        try {
            Trajet saved = trajetService.ajouterTrajetParChauffeur(trajet);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CHAUFFEUR')")
    public ResponseEntity<Trajet> modifierTrajet(@PathVariable Long id, @RequestBody Trajet trajet) {
        Trajet updated = trajetService.modifierTrajet(id, trajet);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CHAUFFEUR')")
    public ResponseEntity<String> supprimerTrajet(@PathVariable Long id) {
        trajetService.supprimerTrajet(id);
        return ResponseEntity.ok("Trajet supprimé avec succès.");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENT','CHAUFFEUR','ADMIN')")
    public List<TrajetDTO> trajets() {
        return  trajetService.getAllTrajets();
    }

    @GetMapping("/count/today")
    public ResponseEntity<Long> getCountTrajetsAjoutesAujourdHui() {
        long count = trajetService.countTrajetsAjoutesAujourdHui();
        return ResponseEntity.ok(count);
    }


}

