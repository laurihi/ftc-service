package com.github.laurihi.ftc.ftcservice.persistence.repository;

import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long>{
}
