package lj.com.ljstaysafe.presenter;

import android.content.Context;

import lj.com.ljstaysafe.contract.MessageSettingsDialogContract;
import lj.com.ljstaysafe.model.MessageSetting;
import lj.com.ljstaysafe.repository.message.MessageSettingRepositoryImpl;

public class MessageSettingPresenterImpl implements MessageSettingsDialogContract.Presenter {

    private MessageSettingsDialogContract.Repository repository;
    private Context context;

    public MessageSettingPresenterImpl(Context context) {
        this.context = context;
        repository = new MessageSettingRepositoryImpl(context);
    }

    @Override
    public void saveSetting(Boolean isAutoReplySms, String autoReplyText) {
        repository.saveSetting(isAutoReplySms, autoReplyText);
    }

    @Override
    public MessageSetting getSetting() {
        return repository.getSetting();
    }
}
