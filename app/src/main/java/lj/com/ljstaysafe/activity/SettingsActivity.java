package lj.com.ljstaysafe.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Objects;

import lj.com.ljstaysafe.R;

public class SettingsActivity extends AppCompatActivity implements ConstraintLayout.OnClickListener {

    private Toolbar toolbar;
    private ConstraintLayout layoutWhitelistContactSettings;

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
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutWhitelistContactSettings:
                Intent intent = new Intent(SettingsActivity.this, WhitelistContactsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
