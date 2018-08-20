package lj.com.ljstaysafe.dialog;

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

public class NotificationSettingsDialog extends DialogFragment {

    private Switch swSilentNotifExceptPhoneCalls;
    private Button btnSave;

    public NotificationSettingsDialog() {
    }

    public static NotificationSettingsDialog newInstance(){
        return new NotificationSettingsDialog();
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
        btnSave = view.findViewById(R.id.btnSave);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = Objects.requireNonNull(getDialog().getWindow()).getAttributes();
        params.width = (getResources().getDisplayMetrics().widthPixels);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

}
