package com.example.louagetunisie.controller;

import com.example.louagetunisie.dto.VehiculeDTO;
import com.example.louagetunisie.model.Vehicule;
import com.example.louagetunisie.repository.VehiculeRepository;
import com.example.louagetunisie.service.VehiculeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;
    private final VehiculeRepository vehiculeRepository;

    public VehiculeController(VehiculeService vehiculeService, VehiculeRepository vehiculeRepository) {
        this.vehiculeService = vehiculeService;
        this.vehiculeRepository = vehiculeRepository;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CHAUFFEUR')")
    public ResponseEntity<Vehicule> ajouterVehicule(@RequestBody Vehicule vehicule) {
        Vehicule saved = vehiculeService.ajouterVehicule(vehicule);
        return ResponseEntity.ok(saved);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CHAUFFEUR')")
    public ResponseEntity<?> supprimerVehicule(@PathVariable Long id) {
            vehiculeService.supprimerVehicule(id);
            return ResponseEntity.ok("Véhicule supprimé avec succès.");
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<VehiculeDTO> listerVehicules() {
        return vehiculeService.afficherToutVoiture();
    }
    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Long count() {
        return vehiculeRepository.countVoitures();
    }
}
