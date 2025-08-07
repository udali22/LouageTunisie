package com.example.louagetunisie.repository;

import com.example.louagetunisie.model.Evaluation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Evaluation e WHERE e.client.id = :clientId")
    void deleteByClientId(@Param("clientId") Long clientId);

}