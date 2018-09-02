package lj.com.ljstaysafe.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import lj.com.ljstaysafe.driving.CheckDrivingStatusService;
import lj.com.ljstaysafe.driving.sensor.SensorReaderFactory;
import lj.com.ljstaysafe.driving.sensor.ThreeAxesSensorReader;
import lj.com.ljstaysafe.fragment.FriendsFragment;
import lj.com.ljstaysafe.fragment.HomeFragment;
import lj.com.ljstaysafe.fragment.MeFragment;
import lj.com.ljstaysafe.repository.driving.DrivingStatusRepositoryImpl;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getName();
    private static final String FENCE_RECEIVER_ACTION = "FENCE_RECEIVE";

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    private GoogleApiClient googleApiClient;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectGoogleApiClient();

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

    private void connectGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Awareness.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerFence();
        Intent intent = new Intent(this, CheckDrivingStatusService.class);
        startService(intent);
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
                            Log.d(TAG, "Fence " + getResources().getString(R.string.driving_status) + " was successfully registered.");
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
                Log.d(TAG, "Fence " + getResources().getString(R.string.driving_status) + " successfully removed.");
            }

            @Override
            public void onFailure(@NonNull Status status) {
                Log.d(TAG, "Fence " + getResources().getString(R.string.driving_status) + " could NOT be removed.");
            }
        });
    }
}
