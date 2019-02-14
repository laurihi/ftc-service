package com.github.laurihi.ftc.ftcservice.persistence.repository;

import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {



    @Query("Select c from Challenge c where c.launchDate <= date and c.endDate >= date")
    List<Challenge> findByStartBeforeAndEndAfter(LocalDate date);
}
