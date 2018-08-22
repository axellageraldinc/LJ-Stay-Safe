package lj.com.ljstaysafe.repository.message;

import android.content.Context;
import android.content.SharedPreferences;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.MessageSettingsDialogContract;
import lj.com.ljstaysafe.model.MessageSetting;

public class MessageSettingRepositoryImpl implements MessageSettingsDialogContract.Repository {

    private Context context;
    private SharedPreferences sharedPreferences;

    public MessageSettingRepositoryImpl(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(MessageSettingRepositoryImpl.class.getName(), Context.MODE_PRIVATE);
    }

    @Override
    public void saveSetting(Boolean isAutoReplySms, String autoReplyText) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.auto_reply_sms_while_driving), isAutoReplySms);
        editor.putString(context.getString(R.string.input_your_sms_message_here), autoReplyText);
        editor.apply();
    }

    @Override
    public MessageSetting getSetting() {
        return MessageSetting.builder()
                .isAutoReplySms(sharedPreferences.getBoolean(context.getString(R.string.auto_reply_sms_while_driving), false))
                .message(sharedPreferences.getString(context.getString(R.string.input_your_sms_message_here), ""))
                .build();
    }
}
