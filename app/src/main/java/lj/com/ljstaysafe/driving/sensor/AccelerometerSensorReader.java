package lj.com.ljstaysafe.driving.sensor;

import lj.com.ljstaysafe.model.SensorHelper;

public class AccelerometerSensorReader implements ThreeAxesSensorReader {
    @Override
    public void read(float x, float y, float z) {
        SensorHelper.ACC_X = x;
        SensorHelper.ACC_Y = y;
        SensorHelper.ACC_Z = z;
    }
}
