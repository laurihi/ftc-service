package com.github.laurihi.ftc.ftcservice.persistence.data;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Participant {

    @Id
    private String userHandle;

    @ManyToMany(mappedBy = "participants")
    private Set<Challenge> challenges = new HashSet<>();


    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public Set<Challenge> getChallenges() {
        return challenges;
    }

    public void addChallenge(Challenge challenge){
        this.challenges.add(challenge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(userHandle, that.userHandle);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userHandle);
    }
}
