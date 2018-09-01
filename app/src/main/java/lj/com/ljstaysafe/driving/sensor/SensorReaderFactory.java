package lj.com.ljstaysafe.driving.sensor;

import android.hardware.Sensor;

import lj.com.ljstaysafe.driving.behaviour_assessment.DistractedBehaviourScoringService;
import lj.com.ljstaysafe.driving.behaviour_assessment.OverallBehaviourScoringService;

public class SensorReaderFactory {
    public static ThreeAxesSensorReader getSensor(int sensorType){
        if(sensorType == Sensor.TYPE_ACCELEROMETER)
            return new AccelerometerSensorReader(
                    new OverallBehaviourScoringService(
                            new DistractedBehaviourScoringService()
                    )
            );
        return null;
    }
}
