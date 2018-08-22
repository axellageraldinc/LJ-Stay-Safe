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
import android.widget.Switch;


import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.NotificationSettingsDialogContract;
import lj.com.ljstaysafe.presenter.NotificationSettingPresenterImpl;

public class NotificationSettingsDialog extends DialogFragment implements Button.OnClickListener {

    private Switch swSilentNotifExceptPhoneCalls;
    private Button btnSave;

    private Context context;

    private NotificationSettingsDialogContract.Presenter presenter;

    public NotificationSettingsDialog() {
    }

    public static NotificationSettingsDialog newInstance(){
        NotificationSettingsDialog notificationSettingsDialog = new NotificationSettingsDialog();
        notificationSettingsDialog.setStyle(STYLE_NO_TITLE, 0);
        return notificationSettingsDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_notification_settings, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swSilentNotifExceptPhoneCalls = view.findViewById(R.id.swSilentNotifExceptPhoneCalls);
        swSilentNotifExceptPhoneCalls.setChecked(presenter.getSetting());
        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        presenter = new NotificationSettingPresenterImpl(context);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                presenter.saveSetting(swSilentNotifExceptPhoneCalls.isChecked());
                dismiss();
                break;
        }
    }
}
