package lj.com.ljstaysafe.driving;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceState;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.DrivingStatusContract;
import lj.com.ljstaysafe.repository.driving.DrivingStatusRepositoryImpl;

public class CheckDrivingStatusBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = CheckDrivingStatusBroadcastReceiver.class.getSimpleName();

    private DrivingStatusContract.Repository drivingStatusRepository;
    private NotificationHandler notificationHandler;
    private Context context;

    public CheckDrivingStatusBroadcastReceiver(Context context) {
        this.context = context;
        drivingStatusRepository = new DrivingStatusRepositoryImpl(context);
        notificationHandler = new NotificationHandler(context);
    }

    public CheckDrivingStatusBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        FenceState fenceState = FenceState.extract(intent);
        Log.d(TAG, "Fence Receiver Received");
        if (fenceState.getFenceKey().equals(context.getResources().getString(R.string.driving_status))) {
            switch (fenceState.getCurrentState()) {
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
    }
}
