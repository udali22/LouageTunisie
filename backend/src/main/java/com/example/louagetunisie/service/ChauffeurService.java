package com.example.louagetunisie.service;

import com.example.louagetunisie.dto.ChauffeurDTO;
import com.example.louagetunisie.model.Chauffeur;
import com.example.louagetunisie.model.Utilisateur;
import com.example.louagetunisie.model.Vehicule;
import com.example.louagetunisie.repository.ChauffeurRepository;
import com.example.louagetunisie.repository.SessionRepository;
import com.example.louagetunisie.repository.TrajetRepository;
import com.example.louagetunisie.repository.VehiculeRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChauffeurService {
    private final ChauffeurRepository chauffeurRepository;
    private final VehiculeRepository vehiculeRepository;
    private final PasswordEncoder passwordEncoder;
    private final TrajetRepository trajetRepository;
    private final SessionRepository sessionRepository;

    public ChauffeurService(ChauffeurRepository chauffeurRepository, VehiculeRepository vehiculeRepository, PasswordEncoder passwordEncoder, TrajetRepository trajetRepository, SessionRepository sessionRepository) {
        this.chauffeurRepository = chauffeurRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.passwordEncoder = passwordEncoder;
        this.trajetRepository = trajetRepository;
        this.sessionRepository = sessionRepository;
    }
    public Chauffeur ajouterChauffeur(Chauffeur chauffeur) {
        if (chauffeur.getVehicule() != null) {
            Long vehiculeId = chauffeur.getVehicule().getId();
            Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                    .orElseThrow(() -> new IllegalArgumentException("Véhicule introuvable"));
            chauffeur.setVehicule(vehicule);
        }
        return chauffeurRepository.save(chauffeur);
    }
    @Transactional
    public void supprimerChauffeur(Long id) {
        if (!chauffeurRepository.existsById(id)) {
            throw new IllegalArgumentException("Chauffeur introuvable avec id: " + id);
        }

        // Supprimer toutes les sessions liées
        sessionRepository.deleteByUtilisateurId(id);

        // Supprimer tous les trajets du chauffeur
        trajetRepository.deleteByChauffeurId(id);

        // (Ajoute d'autres suppressions si nécessaire)

        chauffeurRepository.deleteById(id);
    }

    public List<Chauffeur> getAllChauffeurs() {
        return chauffeurRepository.findAll();
    }
    public Page<ChauffeurDTO> getAllChauffeursAsDTO(Pageable pageable) {
        return chauffeurRepository.findAll(pageable).map(chauffeur ->
                new ChauffeurDTO(
                        chauffeur.getId(),
                        chauffeur.getPermis(),
                        chauffeur.getNom(),
                        chauffeur.getPrenom(),
                        chauffeur.getEmail(),
                        String.valueOf(chauffeur.getTelephone()),
                        chauffeur.getVehicule() != null ? chauffeur.getVehicule().getMatricule() : null,
                        chauffeur.getNumLicence(),
                        chauffeur.getCin()
                )
        );
    }
    public Chauffeur modifierChauffeur(Long id, Chauffeur chauffeurModifie) {
        // 1. Récupération du chauffeur existant
        Chauffeur chauffeurExistant = chauffeurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chauffeur introuvable"));

        // 2. Récupération de l'utilisateur connecté
        Utilisateur authUser = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();



        // 4. Mise à jour conditionnelle des champs (si non null)
        if (chauffeurModifie.getNom() != null) chauffeurExistant.setNom(chauffeurModifie.getNom());
        if (chauffeurModifie.getPrenom() != null) chauffeurExistant.setPrenom(chauffeurModifie.getPrenom());
        if (chauffeurModifie.getEmail() != null) chauffeurExistant.setEmail(chauffeurModifie.getEmail());
        if (chauffeurModifie.getTelephone() != null) chauffeurExistant.setTelephone(chauffeurModifie.getTelephone());
        if (chauffeurModifie.getPermis() != null) chauffeurExistant.setPermis(chauffeurModifie.getPermis());
        if (chauffeurModifie.getNumLicence() != null) chauffeurExistant.setNumLicence(chauffeurModifie.getNumLicence());
        if (chauffeurModifie.getMotDePasse() != null){ String motdepasse = passwordEncoder.encode(chauffeurModifie.getMotDePasse());
        chauffeurExistant.setMotDePasse(motdepasse); }

        // 5. Sauvegarde finale
        return chauffeurRepository.save(chauffeurExistant);
    }



}
