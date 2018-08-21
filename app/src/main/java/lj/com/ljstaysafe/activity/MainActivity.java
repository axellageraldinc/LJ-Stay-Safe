package lj.com.ljstaysafe.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.awareness.fence.HeadphoneFence;
import com.google.android.gms.awareness.state.HeadphoneState;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;

import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.DrivingStatusContract;
import lj.com.ljstaysafe.driving.CheckDrivingStatusBroadcastReceiver;
import lj.com.ljstaysafe.fragment.FriendsFragment;
import lj.com.ljstaysafe.fragment.HomeFragment;
import lj.com.ljstaysafe.fragment.MeFragment;
import lj.com.ljstaysafe.repository.driving.DrivingStatusInteractorImpl;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    private static final String TAG = MainActivity.class.getName();
    private static final String FENCE_RECEIVER_ACTION = "FENCE_RECEIVE";
    private static final float THRESHOLD = 50;

    private long lastUpdate;
    private float lastX, lastY, lastZ;

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    private GoogleApiClient googleApiClient;
    private PendingIntent pendingIntent;
    private CheckDrivingStatusBroadcastReceiver checkDrivingStatusBroadcastReceiver;

    private SensorManager sensorManager;
    private Sensor accelerometer, gravity, gyroscope;

    private DrivingStatusContract.Interactor drivingStatusInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drivingStatusInteractor = new DrivingStatusInteractorImpl(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        registerSensors();

        connectGoogleApiClient();
        checkDrivingStatusBroadcastReceiver = new CheckDrivingStatusBroadcastReceiver(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(HomeFragment.newInstance());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_home:
                HomeFragment homeFragment = HomeFragment.newInstance();
                loadFragment(homeFragment);
                return true;
            case R.id.navigation_friends:
                FriendsFragment friendsFragment = FriendsFragment.newInstance();
                loadFragment(friendsFragment);
                return true;
            case R.id.navigation_me:
                MeFragment meFragment = MeFragment.newInstance();
                loadFragment(meFragment);
                return true;
        }
        return false;
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    private void registerSensors(){
        if(Objects.requireNonNull(sensorManager).getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            if(accelerometer==null) {
                Log.i(TAG, "instantiate accel");
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            }
        } if(Objects.requireNonNull(sensorManager).getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
            if(gravity==null) {
                Log.i(TAG, "instantiate gravity");
                gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
            }
        } if(Objects.requireNonNull(sensorManager).getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            if(gyroscope==null) {
                Log.i(TAG, "instantiate gyro");
                gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            }
        }
    }

    private void connectGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Awareness.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        registerFence();
        registerReceiver(checkDrivingStatusBroadcastReceiver, new IntentFilter(FENCE_RECEIVER_ACTION));
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Temporarily here for unregistering shits (for testing purposes, off screen)
        sensorManager.unregisterListener(this);
        unregisterFence();
        unregisterReceiver(checkDrivingStatusBroadcastReceiver);
    }

    private void registerFence(){
        Intent intent = new Intent(FENCE_RECEIVER_ACTION);
        pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        AwarenessFence headphoneFence = HeadphoneFence.during(HeadphoneState.PLUGGED_IN);
        Awareness.FenceApi.updateFences(
                googleApiClient,
                new FenceUpdateRequest.Builder()
                        .addFence(getResources().getString(R.string.driving_status), headphoneFence, pendingIntent)
                        .build())
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.i(TAG, "Fence " + getResources().getString(R.string.driving_status) + " was successfully registered.");
                        } else {
                            Log.e(TAG, "Fence " + getResources().getString(R.string.driving_status) + " could NOT be registered: " + status);
                        }
                    }
                });
    }
    private void unregisterFence(){
        Awareness.FenceApi.updateFences(
                googleApiClient,
                new FenceUpdateRequest.Builder()
                        .removeFence(getResources().getString(R.string.driving_status))
                        .build()).setResultCallback(new ResultCallbacks<Status>() {
            @Override
            public void onSuccess(@NonNull Status status) {
                Log.i(TAG, "Fence " + getResources().getString(R.string.driving_status) + " successfully removed.");
            }

            @Override
            public void onFailure(@NonNull Status status) {
                Log.i(TAG, "Fence " + getResources().getString(R.string.driving_status) + " could NOT be removed.");
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(drivingStatusInteractor.isDriving()) {
            Sensor sensor = event.sensor;
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                long currentTime = System.currentTimeMillis();
                if ((currentTime - lastUpdate) > 100) {
                    long diffTime = (currentTime - lastUpdate);
                    lastUpdate = currentTime;
                    float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000;
                    if (speed > THRESHOLD) {
                        Log.i(TAG, "SHAKING WITH SPEED : " + speed);
                    }
                    lastX = x;
                    lastY = y;
                    lastZ = z;
                }
            }
            if (sensor.getType() == Sensor.TYPE_GRAVITY) {

            }
            if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
