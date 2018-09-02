package lj.com.ljstaysafe.driving;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Objects;

import lj.com.ljstaysafe.contract.DrivingStatusContract;
import lj.com.ljstaysafe.driving.sensor.SensorReaderFactory;
import lj.com.ljstaysafe.driving.sensor.ThreeAxesSensorReader;
import lj.com.ljstaysafe.driving.sms.SmsReceiver;
import lj.com.ljstaysafe.repository.driving.DrivingStatusRepositoryImpl;

public class CheckDrivingStatusService extends Service implements SensorEventListener {

    private static final String TAG = CheckDrivingStatusService.class.getName();
    private static final String FENCE_RECEIVER_ACTION = "FENCE_RECEIVE";

    private long currentTime, lastTime;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private DrivingStatusContract.Repository drivingStatusRepository;

    private CheckDrivingStatusBroadcastReceiver checkDrivingStatusBroadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        registerSensors();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        drivingStatusRepository = new DrivingStatusRepositoryImpl(this);

        checkDrivingStatusBroadcastReceiver = new CheckDrivingStatusBroadcastReceiver(this);
        registerReceiver(checkDrivingStatusBroadcastReceiver, new IntentFilter(FENCE_RECEIVER_ACTION));
        Log.d(TAG, "Fence Receiver registered");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(checkDrivingStatusBroadcastReceiver!=null){
            unregisterReceiver(checkDrivingStatusBroadcastReceiver);
            Log.d(TAG, "Fence Receiver unregistered");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(drivingStatusRepository.isDriving()) {
            Sensor sensor = event.sensor;
            ThreeAxesSensorReader threeAxesSensorReader = SensorReaderFactory.getSensor(sensor.getType());
            currentTime = System.currentTimeMillis();

            // only read sensors when the interval between previous and current reading is 100 ms
            if(currentTime-lastTime > 100) {
                threeAxesSensorReader.read(event.values[0], event.values[1], event.values[2]);
                lastTime = currentTime;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void registerSensors(){
        if(Objects.requireNonNull(sensorManager).getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            if(accelerometer==null) {
                Log.d(TAG, "instantiate accel");
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            }
        }
    }
}
