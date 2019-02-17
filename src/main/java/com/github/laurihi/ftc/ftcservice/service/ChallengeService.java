package com.github.laurihi.ftc.ftcservice.service;

import com.github.laurihi.ftc.ftcservice.model.challenge.CreateChallenge;
import com.github.laurihi.ftc.ftcservice.model.challenge.CreateRatedExercise;
import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge;
import com.github.laurihi.ftc.ftcservice.persistence.data.Participant;
import com.github.laurihi.ftc.ftcservice.persistence.data.RatedExercise;
import com.github.laurihi.ftc.ftcservice.persistence.repository.ChallengeRepository;
import com.github.laurihi.ftc.ftcservice.persistence.repository.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private ParticipantRepository participantRepository;

    private final Logger LOG = LoggerFactory.getLogger(ChallengeService.class);

    public ChallengeService(@Autowired ChallengeRepository challengeRepository,
                            @Autowired ParticipantRepository participantRepository) {
        this.challengeRepository = challengeRepository;
        this.participantRepository = participantRepository;
    }

    public Challenge create(CreateChallenge challenge) {

        if (doOverlappingChallengesExist(challenge.getStartDate(), challenge.getEndDate())) {
            LOG.warn("Trying to create overlapping challenge by name " + challenge.getName());
            throw new RuntimeException();
        }

        Challenge challengeEntity = mapToChallengeEntity(challenge);
        return challengeRepository.save(challengeEntity);
    }


    public Challenge getOngoingChallenge() {
        LocalDate today = LocalDate.now();
        List<Challenge> ongoing = challengeRepository.findByStartBeforeAndEndAfter(today);

        return ongoing.size() == 0 ? null : ongoing.get(0);
    }

    public boolean hasJoined(String userHandle, Challenge challenge) {

        return challenge.getParticipants().stream()
                .anyMatch(participant -> participant.getUserHandle().equals(userHandle));
    }

    public Participant joinOngoing(String userHandle) {

        Challenge ongoingChallenge = getOngoingChallenge();

        if (ongoingChallenge == null) {
            throw new IllegalStateException("No ongoing challenges to join.");
        }

        Participant participant = participantRepository.findById(userHandle).orElse(addParticipant(userHandle));
        ongoingChallenge.addParticipant(participant);
        challengeRepository.save(ongoingChallenge);

        return participant;
    }


    public Participant getParticipantByUserHandle(String userHandle, Challenge challenge) {
        return challenge.getParticipants().stream()
                .filter(participant -> participant.getUserHandle().equals(userHandle)).findFirst().orElseThrow(IllegalArgumentException::new);

    }

    private Challenge mapToChallengeEntity(CreateChallenge challenge) {
        Challenge challengeEntity = new Challenge();
        challengeEntity.setName(challenge.getName());
        challengeEntity.setStartDate(challenge.getStartDate());
        challengeEntity.setEndDate(challenge.getEndDate());
        challengeEntity.setExercises(mapToRatedExercises(challenge.getExercises()));

        return challengeEntity;
    }

    private List<RatedExercise> mapToRatedExercises(List<CreateRatedExercise> exercises) {
        return exercises.stream().map(exercise -> {
            RatedExercise result = new RatedExercise();
            result.setExerciseKey(exercise.getExerciseKey());
            result.setPointsPerUnit(exercise.getPointsPerUnit());
            result.setUnit(exercise.getUnit());
            return result;
        }).collect(Collectors.toList());
    }

    private boolean doOverlappingChallengesExist(LocalDate start, LocalDate end) {

        List<Challenge> challengesThatEndBeforeCurrentStart = challengeRepository.findByEndDateBefore(start);
        List<Challenge> challengesThatStartAfterCurrentEnd = challengeRepository.findByStartAfter(end);

        List<Challenge> allChallenges = challengeRepository.findAll();

        return challengesThatEndBeforeCurrentStart.size() + challengesThatStartAfterCurrentEnd.size() != allChallenges.size();
    }

    private Participant addParticipant(String userHandle) {
        Participant participant = new Participant();
        participant.setUserHandle(userHandle);
        return participantRepository.save(participant);
    }
}
