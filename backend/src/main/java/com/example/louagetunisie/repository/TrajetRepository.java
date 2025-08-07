package com.example.louagetunisie.repository;

import com.example.louagetunisie.model.Trajet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long> {
    List<Trajet> findByStationDepartIdAndStationArriveeIdAndDateDepartBetween(
            Long stationDepartId,
            Long stationArriveeId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
    long countByDateDepartBetween(LocalDateTime start, LocalDateTime end);
    @Modifying
    @Transactional
    @Query("DELETE FROM Trajet t WHERE t.chauffeur.id = :chauffeurId")
    void deleteByChauffeurId(@Param("chauffeurId") Long chauffeurId);

}
