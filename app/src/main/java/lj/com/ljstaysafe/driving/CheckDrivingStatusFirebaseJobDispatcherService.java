package lj.com.ljstaysafe.driving;

import android.content.IntentFilter;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class CheckDrivingStatusFirebaseJobDispatcherService extends JobService {

    private static final String TAG = CheckDrivingStatusFirebaseJobDispatcherService.class.getName();
    private static final String FENCE_RECEIVER_ACTION = "FENCE_RECEIVE";

    private CheckDrivingStatusBroadcastReceiver checkDrivingStatusBroadcastReceiver;

    @Override
    public boolean onStartJob(JobParameters job) {
        checkDrivingStatusBroadcastReceiver = new CheckDrivingStatusBroadcastReceiver(this);
        registerReceiver(checkDrivingStatusBroadcastReceiver, new IntentFilter(FENCE_RECEIVER_ACTION));
        Log.d(TAG, "Fence Receiver registered");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if(checkDrivingStatusBroadcastReceiver!=null){
            unregisterReceiver(checkDrivingStatusBroadcastReceiver);
            Log.d(TAG, "Fence Receiver unregistered");
        }
        return true;
    }
}
