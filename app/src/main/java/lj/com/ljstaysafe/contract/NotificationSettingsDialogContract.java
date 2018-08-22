package lj.com.ljstaysafe.contract;

public interface NotificationSettingsDialogContract {
    interface Repository {
        void saveSetting(Boolean silentAllNotificationExceptPhoneCalls);
        Boolean getSetting();
    }
    interface Presenter{
        void saveSetting(Boolean silentAllNotificationExceptPhoneCalls);
        Boolean getSetting();
    }
}
