package com.github.laurihi.ftc.ftcservice.persistence.repository;

import com.github.laurihi.ftc.ftcservice.persistence.data.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, String> {

}
