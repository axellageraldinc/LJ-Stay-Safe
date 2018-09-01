package lj.com.ljstaysafe.driving.behaviour_assessment;

import android.util.Log;

import lj.com.ljstaysafe.model.BehaviourScoringHelper;
import lj.com.ljstaysafe.model.SensorHelper;

public class DistractedBehaviourScoringService implements BehaviourScoringService {

    private static final String TAG = DistractedBehaviourScoringService.class.getName();

    @Override
    public double calculateScore() {
        if (
                (SensorHelper.ACC_CURRENT_X <= BehaviourScoringHelper.DISTRACTED_X_CEILING_CONSTRAINT && SensorHelper.ACC_CURRENT_X >= BehaviourScoringHelper.DISTRACTED_X_FLOOR_CONSTRAINT)
                &&
                (SensorHelper.ACC_CURRENT_Y <= BehaviourScoringHelper.DISTRACTED_Y_CEILING_CONSTRAINT && SensorHelper.ACC_CURRENT_Y >= BehaviourScoringHelper.DISTRACTED_Y_FLOOR_CONSTRAINT)
                &&
                (SensorHelper.ACC_CURRENT_Z <= BehaviourScoringHelper.DISTRACTED_Z_CEILING_CONSTRAINT && SensorHelper.ACC_CURRENT_Z >= BehaviourScoringHelper.DISTRACTED_Z_FLOOR_CONSTRAINT)) {
            Log.d(TAG, "DISTRACTED!!!");
            return BehaviourScoringHelper.CURRENT_DISTRACTED_SCORE -= BehaviourScoringHelper.DISTRACTED__MINUS_SCORE_PER_100MS_CONSTANT;
        } else{
            Log.d(TAG, "NOT DISTRACTED :)");
            return BehaviourScoringHelper.CURRENT_DISTRACTED_SCORE;
        }
    }
}
