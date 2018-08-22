package lj.com.ljstaysafe.driving.sensor;

import android.hardware.Sensor;

public class SensorReaderFactory {
    public static ThreeAxesSensorReader getSensor(int sensorType){
        if(sensorType == Sensor.TYPE_ACCELEROMETER)
            return new AccelerometerSensorReader();
        return null;
    }
}
