package lj.com.ljstaysafe.repository.notification;

import android.content.Context;
import android.content.SharedPreferences;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.NotificationSettingsDialogContract;

public class NotificationSettingInteractorImpl implements NotificationSettingsDialogContract.Interactor {

    private SharedPreferences sharedPreferences;
    private Context context;

    public NotificationSettingInteractorImpl(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(NotificationSettingInteractorImpl.class.getName(), Context.MODE_PRIVATE);
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
