package com.example.louagetunisie.repository;

import com.example.louagetunisie.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCin(int cin);

    long countByRole(String client);

    @Query("SELECT COUNT(u) FROM Utilisateur u")
    Long countUser();
}