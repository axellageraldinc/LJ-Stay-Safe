package lj.com.ljstaysafe.repository.notification;

import android.content.Context;
import android.content.SharedPreferences;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.NotificationSettingsDialogContract;

public class NotificationSettingRepositoryImpl implements NotificationSettingsDialogContract.Repository {

    private SharedPreferences sharedPreferences;
    private Context context;

    public NotificationSettingRepositoryImpl(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(NotificationSettingRepositoryImpl.class.getName(), Context.MODE_PRIVATE);
    }

    @Override
    public void saveSetting(Boolean silentAllNotificationExceptPhoneCalls) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.silent_all_notifications_except_phone_calls), silentAllNotificationExceptPhoneCalls);
        editor.apply();
    }

    @Override
    public Boolean getSetting() {
        return sharedPreferences.getBoolean(context.getString(R.string.silent_all_notifications_except_phone_calls), false);
    }
}
