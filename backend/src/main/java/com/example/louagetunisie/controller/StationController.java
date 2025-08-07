package com.example.louagetunisie.controller;

import com.example.louagetunisie.dto.StationDTO;
import com.example.louagetunisie.model.Station;
import com.example.louagetunisie.repository.StationRepository;
import com.example.louagetunisie.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
public class StationController {

    private final StationService stationService;
    private StationRepository stationRepository;

    public StationController(StationService stationService, StationRepository stationRepository) {
        this.stationService = stationService;
        this.stationRepository = stationRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Station> ajouterStation(@RequestBody Station station) {
        Station saved = stationService.ajouterStation(station);
        return ResponseEntity.ok(saved);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> supprimerStation(@PathVariable Long id) {
        stationService.supprimerStation(id);
        return ResponseEntity.ok("Station supprimée avec succès.");
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CHAUFFEUR','CLIENT')")
    public List<StationDTO> getAllStations() {
        return stationService.afficherStations();
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Long count() {
        return stationRepository.countStations();
    }
}
