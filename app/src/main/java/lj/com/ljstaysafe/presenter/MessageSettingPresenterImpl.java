package lj.com.ljstaysafe.presenter;

import android.content.Context;

import lj.com.ljstaysafe.contract.MessageSettingsDialogContract;
import lj.com.ljstaysafe.model.MessageSetting;
import lj.com.ljstaysafe.repository.message.MessageSettingInteractorImpl;

public class MessageSettingPresenterImpl implements MessageSettingsDialogContract.Presenter {

    private MessageSettingsDialogContract.Interactor interactor;
    private Context context;

    public MessageSettingPresenterImpl(Context context) {
        this.context = context;
        interactor = new MessageSettingInteractorImpl(context);
    }

    @Override
    public void saveSetting(Boolean isAutoReplySms, String autoReplyText) {
        interactor.saveSetting(isAutoReplySms, autoReplyText);
    }

    @Override
    public MessageSetting getSetting() {
        return interactor.getSetting();
    }
}
