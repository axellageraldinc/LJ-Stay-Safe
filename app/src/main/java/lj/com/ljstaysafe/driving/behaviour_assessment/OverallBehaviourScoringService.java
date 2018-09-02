package lj.com.ljstaysafe.driving.behaviour_assessment;

import lj.com.ljstaysafe.model.BehaviourScoringHelper;

public class OverallBehaviourScoringService implements BehaviourScoringService {

    private static final int BEHAVIOUR_TOTAL = 1;

    private double behaviourScore=0;

    private BehaviourScoringService distractedBehaviourScoringService;

    public OverallBehaviourScoringService(BehaviourScoringService distractedBehaviourScoringService) {
        this.distractedBehaviourScoringService = distractedBehaviourScoringService;
    }

    @Override
    public double calculateScore() {
        behaviourScore = ((distractedBehaviourScoringService.calculateScore()) / BEHAVIOUR_TOTAL);
        return BehaviourScoringHelper.CURRENT_OVERALL_SCORE = behaviourScore;
    }
}
