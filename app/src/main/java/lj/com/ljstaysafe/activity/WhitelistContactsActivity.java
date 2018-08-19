package lj.com.ljstaysafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.RecyclerViewWhitelistContactAdapter;
import lj.com.ljstaysafe.model.WhitelistContact;

public class WhitelistContactsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvWhitelistContacts;
    private RecyclerViewWhitelistContactAdapter rvWhitelistContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whitelist_contacts);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvWhitelistContacts = findViewById(R.id.rvWhitelistContacts);
        rvWhitelistContacts.setLayoutManager(new LinearLayoutManager(WhitelistContactsActivity.this));
        rvWhitelistContactAdapter = new RecyclerViewWhitelistContactAdapter(new ArrayList<WhitelistContact>());
        rvWhitelistContacts.setAdapter(rvWhitelistContactAdapter);
        rvWhitelistContactAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
