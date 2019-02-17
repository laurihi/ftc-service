package com.github.laurihi.ftc.ftcservice.model.challenge;

import java.time.LocalDate;

public class ChallengeBase {

    private String challengeName;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
