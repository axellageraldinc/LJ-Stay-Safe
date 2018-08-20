package lj.com.ljstaysafe.contract;

public interface NotificationSettingsDialogContract {
    interface Interactor{
        void saveSetting(Boolean silentAllNotificationExceptPhoneCalls);
        Boolean getSetting();
    }
    interface Presenter{
        void saveSetting(Boolean silentAllNotificationExceptPhoneCalls);
        Boolean getSetting();
    }
}
