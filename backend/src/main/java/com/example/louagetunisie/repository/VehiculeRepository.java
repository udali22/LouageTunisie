package com.example.louagetunisie.repository;

import com.example.louagetunisie.model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    Long id(Long id);

    Optional<Vehicule> findByMatricule(String matricule);
    @Query("SELECT COUNT(v) FROM Vehicule v")
    Long countVoitures();

}
