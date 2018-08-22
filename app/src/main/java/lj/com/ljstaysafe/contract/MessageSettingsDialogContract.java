package lj.com.ljstaysafe.contract;

import lj.com.ljstaysafe.model.MessageSetting;

public interface MessageSettingsDialogContract {
    interface Repository {
        void saveSetting(Boolean isAutoReplySms, String autoReplyText);
        MessageSetting getSetting();
    }
    interface Presenter{
        void saveSetting(Boolean isAutoReplySms, String autoReplyText);
        MessageSetting getSetting();
    }
}
