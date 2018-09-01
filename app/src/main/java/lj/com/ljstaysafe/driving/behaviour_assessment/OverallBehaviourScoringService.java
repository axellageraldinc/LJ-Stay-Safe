package lj.com.ljstaysafe.driving.behaviour_assessment;

import lj.com.ljstaysafe.model.BehaviourScoringHelper;

public class OverallBehaviourScoringService implements BehaviourScoringService {

    private static final int BEHAVIOUR_TOTAL = 1;

    private BehaviourScoringService distractedBehaviourScoringService;

    public OverallBehaviourScoringService(BehaviourScoringService distractedBehaviourScoringService) {
        this.distractedBehaviourScoringService = distractedBehaviourScoringService;
    }

    @Override
    public double calculateScore() {
        return BehaviourScoringHelper.CURRENT_OVERALL_SCORE -= ((distractedBehaviourScoringService.calculateScore()) / BEHAVIOUR_TOTAL);
    }
}
