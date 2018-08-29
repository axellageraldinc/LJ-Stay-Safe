package lj.com.ljstaysafe.driving;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceState;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.DrivingStatusContract;
import lj.com.ljstaysafe.repository.driving.DrivingStatusRepositoryImpl;

public class FenceStatusHandlerService extends Service {

    private static final String TAG = FenceStatusHandlerService.class.getName();

    private Boolean isBackgroundThreadRunning;
    private Thread backgroundThread;
    private DrivingStatusContract.Repository drivingStatusRepository;
    private NotificationHandler notificationHandler;

    private String fenceKey;
    private int fenceState;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, TAG + " created...");
        isBackgroundThreadRunning = false;
        drivingStatusRepository = new DrivingStatusRepositoryImpl(this);
        notificationHandler = new NotificationHandler(this);
        backgroundThread = new Thread(fenceStatusChangedTask);
    }

    private Runnable fenceStatusChangedTask = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "fence status changed...");
            if(fenceKey.equals(getResources().getString(R.string.driving_status))) {
                switch (fenceState) {
                    case FenceState.TRUE:
                        if (!drivingStatusRepository.isPassenger()) {
                            drivingStatusRepository.saveDrivingStatus(true);
                            notificationHandler.createNotification(
                                    true,
                                    "No phone while driving!!!",
                                    "Click here if you are a passenger",
                                    true);
                        }
                        break;
                    case FenceState.FALSE:
                        if (!drivingStatusRepository.isPassenger()) {
                            drivingStatusRepository.saveDrivingStatus(false);
                        } else {
                            drivingStatusRepository.savePassengerStatus(false);
                        }
                        notificationHandler.createNotification(
                                false,
                                "Driving is over",
                                "Click here to see your driving score",
                                false);
                        break;
                    case FenceState.UNKNOWN:
                        if (!drivingStatusRepository.isPassenger()) {
                            drivingStatusRepository.saveDrivingStatus(false);
                        } else {
                            drivingStatusRepository.savePassengerStatus(false);
                        }
                        break;
                }
            }
            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        isBackgroundThreadRunning=false;
        Log.d(TAG, "FenceStatusHandlerService destroyed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isBackgroundThreadRunning) {
            isBackgroundThreadRunning = true;
            backgroundThread.start();
            fenceKey = intent.getStringExtra("fenceKey");
            fenceState = intent.getIntExtra("fenceState", 0);
        }
        return START_STICKY;
    }
}
