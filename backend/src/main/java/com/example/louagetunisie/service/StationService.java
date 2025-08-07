package com.example.louagetunisie.service;

import com.example.louagetunisie.dto.StationDTO;
import com.example.louagetunisie.model.Station;
import com.example.louagetunisie.repository.StationRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Station ajouterStation(Station station) {
        return stationRepository.save(station);
    }

    public void supprimerStation(Long stationId) {
        if (!stationRepository.existsById(stationId)) {
            throw new IllegalArgumentException("Station introuvable avec id: " + stationId);
        }
        try {
            stationRepository.deleteById(stationId);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("Impossible de supprimer cette station car elle est utilisée dans des trajets.");
        }
    }
    public List<StationDTO> afficherStations() {
        return stationRepository.findAll().stream().map(station ->
                new StationDTO(
                        station.getId(),
                        station.getNom(),
                        station.getVille(),
                        station.getAdresse()
                )
        ).collect(Collectors.toList());
    }

}
