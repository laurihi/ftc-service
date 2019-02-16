package com.github.laurihi.ftc.ftcservice.persistence.repository;

import com.github.laurihi.ftc.ftcservice.persistence.data.actions.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

}
