package lj.com.ljstaysafe.repository.driving;

import android.content.Context;
import android.content.SharedPreferences;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.DrivingStatusContract;

public class DrivingStatusInteractorImpl implements DrivingStatusContract.Interactor {
    private SharedPreferences sharedPreferences;
    private Context context;

    public DrivingStatusInteractorImpl(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.driving_status), Context.MODE_PRIVATE);
    }

    @Override
    public void saveDrivingStatus(Boolean isDriving) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.driving_status), isDriving);
        editor.apply();
    }

    @Override
    public Boolean getDrivingStatus(Boolean isDriving) {
        return sharedPreferences.getBoolean(context.getString(R.string.driving_status), false);
    }
}