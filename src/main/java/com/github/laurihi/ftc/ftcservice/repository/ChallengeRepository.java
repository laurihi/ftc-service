package com.github.laurihi.ftc.ftcservice.repository;

import com.github.laurihi.ftc.ftcservice.model.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long>{
}
