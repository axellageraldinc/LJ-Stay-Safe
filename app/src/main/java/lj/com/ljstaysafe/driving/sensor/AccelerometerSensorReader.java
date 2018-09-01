package lj.com.ljstaysafe.driving.sensor;

import lj.com.ljstaysafe.driving.behaviour_assessment.BehaviourScoringService;
import lj.com.ljstaysafe.model.SensorHelper;

public class AccelerometerSensorReader implements ThreeAxesSensorReader {

    private BehaviourScoringService overallBehaviourScoringService;

    public AccelerometerSensorReader(BehaviourScoringService overallBehaviourScoringService) {
        this.overallBehaviourScoringService = overallBehaviourScoringService;
    }

    @Override
    public void read(float x, float y, float z) {
        SensorHelper.ACC_CURRENT_X = x;
        SensorHelper.ACC_CURRENT_Y = y;
        SensorHelper.ACC_CURRENT_Z = z;
        SensorHelper.ACC_LAST_X = SensorHelper.ACC_CURRENT_X;
        SensorHelper.ACC_LAST_Y = SensorHelper.ACC_CURRENT_Y;
        SensorHelper.ACC_LAST_Z = SensorHelper.ACC_CURRENT_Z;
        overallBehaviourScoringService.calculateScore();
    }
}
