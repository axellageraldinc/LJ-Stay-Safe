package lj.com.ljstaysafe.driving;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceState;

import lj.com.ljstaysafe.contract.DrivingStatusContract;
import lj.com.ljstaysafe.repository.driving.DrivingStatusInteractorImpl;

public class CheckDrivingStatusBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = CheckDrivingStatusBroadcastReceiver.class.getSimpleName();

    private DrivingStatusContract.Interactor drivingStatusInteractor;
    private NotificationHandler notificationHandler;
    private Context context;

    public CheckDrivingStatusBroadcastReceiver(Context context) {
        this.context = context;
        drivingStatusInteractor = new DrivingStatusInteractorImpl(context);
        notificationHandler = new NotificationHandler(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        FenceState fenceState = FenceState.extract(intent);
        Log.i(TAG, "Fence Receiver Received");
        switch (fenceState.getCurrentState()) {
            case FenceState.TRUE:
                if (!drivingStatusInteractor.isPassenger()) {
                    drivingStatusInteractor.saveDrivingStatus(true);
                    notificationHandler.createNotification(
                            true,
                            "No phone while driving!!!",
                            "Click here if you are a passenger",
                            true);
                }
                break;
            case FenceState.FALSE:
                if (!drivingStatusInteractor.isPassenger()) {
                    drivingStatusInteractor.saveDrivingStatus(false);
                } else {
                    drivingStatusInteractor.savePassengerStatus(false);
                }
                notificationHandler.createNotification(
                        false,
                        "Driving is over",
                        "Click here to see your driving score",
                        false);
                break;
            case FenceState.UNKNOWN:
                if (!drivingStatusInteractor.isPassenger()) {
                    drivingStatusInteractor.saveDrivingStatus(false);
                } else {
                    drivingStatusInteractor.savePassengerStatus(false);
                }
                break;
        }
    }
}
