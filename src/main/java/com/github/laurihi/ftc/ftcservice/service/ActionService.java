package com.github.laurihi.ftc.ftcservice.service;

import com.github.laurihi.ftc.ftcservice.model.DailyAction;
import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge;
import com.github.laurihi.ftc.ftcservice.persistence.data.Participant;
import com.github.laurihi.ftc.ftcservice.persistence.data.RatedExercise;
import com.github.laurihi.ftc.ftcservice.persistence.data.actions.Action;
import com.github.laurihi.ftc.ftcservice.persistence.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ActionService {

    private final ActionRepository actionRepository;
    private final ChallengeService challengeService;

    public ActionService(@Autowired ActionRepository actionRepository,
                         @Autowired ChallengeService challengeService) {
        this.actionRepository = actionRepository;
        this.challengeService = challengeService;
    }

    public void addDailyActions(String userHandle, List<DailyAction> actions, LocalDate date) {


        Challenge ongoingChallenge = challengeService.getOngoingChallenge();

        List<RatedExercise> availableExercises = ongoingChallenge.getExercises();

        Action entity = new Action();
    }

    private List<Action> mapToActions(List<DailyAction> dailyActions, LocalDate date, Challenge challenge, Participant participant) {

        List<RatedExercise> availableExercises = challenge.getExercises();

        Map<String, RatedExercise> ratedExerciseMap =
                availableExercises.stream().collect(Collectors.toMap(RatedExercise::getExerciseKey,
                        Function.identity()));

        List<Action> actions = dailyActions.stream()
                .filter(dailyAction -> {
                    return ratedExerciseMap.containsKey(dailyAction.getExercise());
                }).map(dailyAction -> {
                    Action entry = new Action();
                    entry.setActionDate(date);
                    entry.setAmountDone(dailyAction.getAmountOf());
                    entry.setExercise(ratedExerciseMap.get(dailyAction.getExercise()));
                    entry.setChallenge(challenge);
                    return entry;
                }).collect(Collectors.toList());
        return actions;
    }

}
