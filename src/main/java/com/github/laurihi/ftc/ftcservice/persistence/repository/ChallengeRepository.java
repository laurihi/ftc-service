package com.github.laurihi.ftc.ftcservice.persistence.repository;

import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {



    @Query("Select c from Challenge c where c.startDate <= :date and c.endDate >= :date")
    List<Challenge> findByStartBeforeAndEndAfter(@Param("date") LocalDate date);

    @Query("Select c from Challenge c where c.startDate >= :date")
    List<Challenge> findByStartAfter(@Param("date") LocalDate date);

    @Query("Select c from Challenge c where c.endDate <= :date")
    List<Challenge> findByEndDateBefore(@Param("date") LocalDate date);
}
