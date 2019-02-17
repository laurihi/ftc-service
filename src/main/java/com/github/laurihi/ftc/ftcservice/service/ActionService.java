package com.github.laurihi.ftc.ftcservice.service;

import com.github.laurihi.ftc.ftcservice.model.actions.DailyActionsWrapper;
import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge;
import com.github.laurihi.ftc.ftcservice.persistence.data.Participant;
import com.github.laurihi.ftc.ftcservice.persistence.data.RatedExercise;
import com.github.laurihi.ftc.ftcservice.persistence.data.actions.Action;
import com.github.laurihi.ftc.ftcservice.persistence.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addDailyActions(DailyActionsWrapper dailyActionsWrapper) {


        Challenge ongoingChallenge = challengeService.getOngoingChallenge();
        List<Action> actions = mapToActions(dailyActionsWrapper, ongoingChallenge);
        actionRepository.saveAll(actions);


    }

    //TODO: extract as class and test.
    private List<Action> mapToActions(DailyActionsWrapper dailyActionsWrapper, Challenge challenge) {

        List<RatedExercise> availableExercises = challenge.getExercises();

        Map<String, RatedExercise> ratedExerciseMap =
                availableExercises.stream().collect(Collectors.toMap(RatedExercise::getExerciseKey,
                        Function.identity()));

        Participant participant = challengeService.hasJoined(dailyActionsWrapper.getUserHandle(), challenge) ?
                challengeService.getParticipantByUserHandle(dailyActionsWrapper.getUserHandle(), challenge) :
                challengeService.joinOngoing(dailyActionsWrapper.getUserHandle());


        List<Action> actions = dailyActionsWrapper.getDailyActions().stream()
                .filter(dailyAction -> ratedExerciseMap.containsKey(dailyAction.getExercise()))
                .map(dailyAction -> {
                    Action entry = new Action();
                    entry.setActionDate(dailyActionsWrapper.getDate());
                    entry.setAmountDone(dailyAction.getAmountOf());
                    entry.setExercise(ratedExerciseMap.get(dailyAction.getExercise()));
                    entry.setChallenge(challenge);
                    entry.setParticipant(participant);
                    return entry;
                }).collect(Collectors.toList());

        return actions;
    }

}
