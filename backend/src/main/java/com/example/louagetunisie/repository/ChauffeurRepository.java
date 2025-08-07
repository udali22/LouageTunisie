package com.example.louagetunisie.repository;

import com.example.louagetunisie.model.Chauffeur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {
    Optional<Chauffeur> findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByPermis(String permis);

    boolean existsByNumLicence(Long numLicence);
}