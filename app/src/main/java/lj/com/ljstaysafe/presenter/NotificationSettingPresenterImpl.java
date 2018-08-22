package lj.com.ljstaysafe.presenter;

import android.content.Context;

import lj.com.ljstaysafe.contract.NotificationSettingsDialogContract;
import lj.com.ljstaysafe.repository.notification.NotificationSettingRepositoryImpl;

public class NotificationSettingPresenterImpl implements NotificationSettingsDialogContract.Presenter {

    private NotificationSettingsDialogContract.Repository repository;
    private Context context;

    public NotificationSettingPresenterImpl(Context context) {
        this.context = context;
        this.repository = new NotificationSettingRepositoryImpl(context);
    }

    @Override
    public void saveSetting(Boolean silentAllNotificationExceptPhoneCalls) {
        repository.saveSetting(silentAllNotificationExceptPhoneCalls);
    }

    @Override
    public Boolean getSetting() {
        return repository.getSetting();
    }
}
