package com.github.laurihi.ftc.ftcservice.persistence.data;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Challenge {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    private LocalDate launchDate;
    private LocalDate endDate;


    @OneToMany(targetEntity = RatedExercise.class, fetch = FetchType.EAGER)
    private List<RatedExercise> exercises = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(joinColumns = @JoinColumn(name = "challenge_id"), inverseJoinColumns = @JoinColumn(name = "participant_handle")
    )
    private Set<Participant> participants = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<RatedExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<RatedExercise> exercises) {
        this.exercises = exercises;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void addParticipant(Participant participant){
        participants.add(participant);
    }
}
