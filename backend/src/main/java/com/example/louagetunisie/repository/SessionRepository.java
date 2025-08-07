package com.example.louagetunisie.repository;

import com.example.louagetunisie.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByToken(String token);
    @Query("""
        SELECT s.utilisateur.id FROM Session s
        WHERE s.expiration > CURRENT_TIMESTAMP
    """)
    List<Long> findActiveUserIds();
    @Modifying
    @Query("DELETE FROM Session s WHERE s.utilisateur.id = :id")
    void deleteByUtilisateurId(Long id);


}
