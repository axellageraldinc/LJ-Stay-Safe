package lj.com.ljstaysafe.driving;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class CheckDrivingStatusService extends Service {

    private static final String TAG = CheckDrivingStatusService.class.getName();
    private static final String FENCE_RECEIVER_ACTION = "FENCE_RECEIVE";

    private CheckDrivingStatusBroadcastReceiver checkDrivingStatusBroadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
}
