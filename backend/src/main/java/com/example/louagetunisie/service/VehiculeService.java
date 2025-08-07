package com.example.louagetunisie.service;

import com.example.louagetunisie.dto.VehiculeDTO;
import com.example.louagetunisie.model.Vehicule;
import com.example.louagetunisie.repository.VehiculeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculeService {
    private final VehiculeRepository vehiculeRepository;

    public VehiculeService(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    public Vehicule ajouterVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }
    public void supprimerVehicule(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new IllegalArgumentException("Véhicule introuvable avec id: " + id);
        }

        try {
            vehiculeRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("Impossible de supprimer ce véhicule car il est associé à un trajet ou à un chauffeur.");
        }
    }
    public List<VehiculeDTO> afficherToutVoiture() {
        return vehiculeRepository.findAll().stream().map(voiture ->(
                new VehiculeDTO(
                        voiture.getId(),
                        voiture.getMatricule(),
                        voiture.getMarque(),
                        voiture.getModele(),
                        voiture.getCapacite()
                )
                )).collect(Collectors.toList());
    }


}
