package com.github.laurihi.ftc.ftcservice.persistence.data.actions;

import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge;
import com.github.laurihi.ftc.ftcservice.persistence.data.Participant;
import com.github.laurihi.ftc.ftcservice.persistence.data.RatedExercise;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    LocalDate actionDate;

    @OneToOne
    private Participant participant;

    @OneToOne
    private Challenge challenge;

    @OneToOne(targetEntity = RatedExercise.class)
    private RatedExercise exercise;

    private Long amountDone;
    private Long pointsGained;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public RatedExercise getExercise() {
        return exercise;
    }

    public void setExercise(RatedExercise exercise) {
        this.exercise = exercise;
    }

    public Long getAmountDone() {
        return amountDone;
    }

    public void setAmountDone(Long amountDone) {
        this.amountDone = amountDone;
    }

    public Long getPointsGained() {
        return pointsGained;
    }

    public void setPointsGained(Long pointsGained) {
        this.pointsGained = pointsGained;
    }
}
