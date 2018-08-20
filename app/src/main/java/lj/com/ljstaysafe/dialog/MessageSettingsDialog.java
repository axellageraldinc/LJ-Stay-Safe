package lj.com.ljstaysafe.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.MessageSettingsDialogContract;
import lj.com.ljstaysafe.model.MessageSetting;
import lj.com.ljstaysafe.presenter.MessageSettingPresenterImpl;

public class MessageSettingsDialog extends DialogFragment implements Button.OnClickListener {

    private Switch swAutoReplySmsWhileDriving;
    private EditText etSmsMessage;
    private Button btnSave;

    private MessageSettingsDialogContract.Presenter presenter;

    public MessageSettingsDialog() {
    }

    public static MessageSettingsDialog newInstance(){
        return new MessageSettingsDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_message_settings, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MessageSetting setting = presenter.getSetting();
        swAutoReplySmsWhileDriving = view.findViewById(R.id.swAutoReplySmsWhileDriving);
        swAutoReplySmsWhileDriving.setChecked(setting.getIsAutoReplySms());
        etSmsMessage = view.findViewById(R.id.etSmsMessage);
        etSmsMessage.setText(setting.getMessage());
        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = Objects.requireNonNull(getDialog().getWindow()).getAttributes();
        params.width = (getResources().getDisplayMetrics().widthPixels);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = new MessageSettingPresenterImpl(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                presenter.saveSetting(swAutoReplySmsWhileDriving.isChecked(), etSmsMessage.getText().toString());
                dismiss();
                break;
        }
    }
}
