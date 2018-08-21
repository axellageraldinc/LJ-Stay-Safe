package lj.com.ljstaysafe.driving;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class DrivingStatusHandlerService extends IntentService {

    private static final String TAG = DrivingStatusHandlerService.class.getName();

    private String fenceState, fenceKey;

    public DrivingStatusHandlerService() {
        super("DrivingStatusHandlerService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        fenceState = String.valueOf(intent.getIntExtra("fenceState", 0));
        fenceKey = intent.getStringExtra("fenceKey");
        Log.i(TAG, "State : " + fenceState + ", Key : " + fenceKey);
    }
}
