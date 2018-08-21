package lj.com.ljstaysafe.driving;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceState;

import lj.com.ljstaysafe.contract.DrivingStatusContract;
import lj.com.ljstaysafe.repository.driving.DrivingStatusInteractorImpl;

public class DrivingStatusHandlerService extends IntentService {

    private static final String TAG = DrivingStatusHandlerService.class.getName();

    private Integer fenceState;
    private String fenceKey;

    private DrivingStatusContract.Interactor drivingStatusInteractor;
    private NotificationHandler notificationHandler;

    public DrivingStatusHandlerService() {
        super("DrivingStatusHandlerService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        drivingStatusInteractor = new DrivingStatusInteractorImpl(this);
        notificationHandler = new NotificationHandler(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        fenceState = intent.getIntExtra("fenceState", 0);
        fenceKey = intent.getStringExtra("fenceKey");
        Log.i(TAG, "State : " + fenceState + ", Key : " + fenceKey);
        switch (fenceState){
            case FenceState.TRUE:
                if(!drivingStatusInteractor.isPassenger()) {
                    drivingStatusInteractor.saveDrivingStatus(true);
                    notificationHandler.createNotification(
                            true,
                            "No phone while driving!!!",
                            "Click here if you are a passenger",
                            true);
                }
                break;
            case FenceState.FALSE:
                if(!drivingStatusInteractor.isPassenger()) {
                    drivingStatusInteractor.saveDrivingStatus(false);
                } else{
                    drivingStatusInteractor.savePassengerStatus(false);
                }
                notificationHandler.createNotification(
                        false,
                        "Driving is over",
                        "Click here to see your driving score",
                        false);
                break;
            case FenceState.UNKNOWN:
                if(!drivingStatusInteractor.isPassenger()) {
                    drivingStatusInteractor.saveDrivingStatus(false);
                } else{
                    drivingStatusInteractor.savePassengerStatus(false);
                }
                break;
        }
    }
}
