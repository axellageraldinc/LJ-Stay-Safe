package lj.com.ljstaysafe.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.dialog.MessageSettingsDialog;
import lj.com.ljstaysafe.dialog.NotificationSettingsDialog;

public class SettingsActivity extends AppCompatActivity implements ConstraintLayout.OnClickListener {

    private static final String NOTIFICATION_SETTINGS_DIALOG_TAG = "notification_settings_dialog";
    private static final String MESSAGE_SETTINGS_DIALOG_TAG = "message_settings_dialog";

    private Toolbar toolbar;
    private ConstraintLayout layoutWhitelistContactSettings, layoutNotificationSettings, layoutMessageSettings, layoutDeveloperMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutWhitelistContactSettings = findViewById(R.id.layoutWhitelistContactSettings);
        layoutWhitelistContactSettings.setOnClickListener(this);
        layoutNotificationSettings = findViewById(R.id.layoutNotificationSettings);
        layoutNotificationSettings.setOnClickListener(this);
        layoutMessageSettings = findViewById(R.id.layoutMessageSettings);
        layoutMessageSettings.setOnClickListener(this);
        layoutMessageSettings.setVisibility(View.GONE);
        layoutDeveloperMenu = findViewById(R.id.layoutDeveloperMenu);
        layoutDeveloperMenu.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (v.getId()){
            case R.id.layoutWhitelistContactSettings:
                Intent intent = new Intent(SettingsActivity.this, WhitelistContactsActivity.class);
                startActivity(intent);
                break;
            case R.id.layoutNotificationSettings:
                DialogFragment notificationSettingsDialog = NotificationSettingsDialog.newInstance();
                notificationSettingsDialog.setCancelable(true);
                notificationSettingsDialog.show(fragmentManager, NOTIFICATION_SETTINGS_DIALOG_TAG);
                break;
            case R.id.layoutMessageSettings:
                DialogFragment messageSettingsDialog = MessageSettingsDialog.newInstance();
                messageSettingsDialog.setCancelable(true);
                messageSettingsDialog.show(fragmentManager, MESSAGE_SETTINGS_DIALOG_TAG);
                break;
            case R.id.layoutDeveloperMenu:
                Intent developerMenuIntent = new Intent(SettingsActivity.this, DeveloperActivity.class);
                startActivity(developerMenuIntent);
                break;
        }
    }
}
