package com.example.louagetunisie.controller;

import com.example.louagetunisie.dto.ChauffeurDTO;
import com.example.louagetunisie.dto.ClientDTO;
import com.example.louagetunisie.model.Chauffeur;
import com.example.louagetunisie.repository.UtilisateurRepository;
import com.example.louagetunisie.service.ChauffeurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chauffeurs")
public class ChauffeurController {

    private final ChauffeurService chauffeurService;
    private UtilisateurRepository utilisateurRepository;

    public ChauffeurController(ChauffeurService chauffeurService, UtilisateurRepository utilisateurRepository) {
        this.chauffeurService = chauffeurService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Chauffeur> ajouterChauffeur(@RequestBody Chauffeur chauffeur) {
        Chauffeur saved = chauffeurService.ajouterChauffeur(chauffeur);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteChauffeur(@PathVariable Long id) {
        chauffeurService.supprimerChauffeur(id);
        return ResponseEntity.ok("chauffeur supprimé avec succés");
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ChauffeurDTO>> getAllChauffeurs(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "100") int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<ChauffeurDTO> chauffeurs = chauffeurService.getAllChauffeursAsDTO(pageable);
        return ResponseEntity.ok(chauffeurs);
    }

    @PutMapping("/alter/{id}")
    @PreAuthorize("hasRole('CHAUFFEUR')")
    public Chauffeur altererChauffeur(@PathVariable Long id , @RequestBody Chauffeur chauffeur) throws AccessDeniedException {
        chauffeurService.modifierChauffeur(id, chauffeur);
        return chauffeur;
    }
    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Long countAllChauffeurs() {
        return utilisateurRepository.countByRole("CHAUFFEUR");
    }

}

