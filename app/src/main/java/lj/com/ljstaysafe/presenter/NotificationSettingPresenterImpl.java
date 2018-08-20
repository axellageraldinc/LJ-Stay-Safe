package lj.com.ljstaysafe.presenter;

import android.content.Context;

import lj.com.ljstaysafe.contract.NotificationSettingsDialogContract;
import lj.com.ljstaysafe.repository.NotificationSettingInteractorImpl;

public class NotificationSettingPresenterImpl implements NotificationSettingsDialogContract.Presenter {

    private NotificationSettingsDialogContract.Interactor interactor;
    private Context context;

    public NotificationSettingPresenterImpl(Context context) {
        this.context = context;
        this.interactor = new NotificationSettingInteractorImpl(context);
    }

    @Override
    public void saveSetting(Boolean silentAllNotificationExceptPhoneCalls) {
        interactor.saveSetting(silentAllNotificationExceptPhoneCalls);
    }

    @Override
    public Boolean getSetting() {
        return interactor.getSetting();
    }
}
