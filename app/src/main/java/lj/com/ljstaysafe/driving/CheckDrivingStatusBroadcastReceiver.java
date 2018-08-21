package lj.com.ljstaysafe.driving;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceState;

public class CheckDrivingStatusBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = CheckDrivingStatusBroadcastReceiver.class.getSimpleName();

    public CheckDrivingStatusBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        FenceState fenceState = FenceState.extract(intent);
        Log.i(TAG, "Fence Receiver Received");
        Intent fenceBackgroundService = new Intent(context, DrivingStatusHandlerService.class);
        fenceBackgroundService.putExtra("fenceState", fenceState.getCurrentState());
        fenceBackgroundService.putExtra("fenceKey", fenceState.getFenceKey());
        context.startService(fenceBackgroundService);
    }
}
