package lj.com.ljstaysafe.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;
import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.view.recyclerview_adapter.RecyclerViewWhitelistContactAdapter;
import lj.com.ljstaysafe.contract.WhitelistContactContract;
import lj.com.ljstaysafe.model.WhitelistContact;
import lj.com.ljstaysafe.presenter.WhitelistContactPresenterImpl;

public class WhitelistContactsActivity extends AppCompatActivity implements WhitelistContactContract.View, FloatingActionButton.OnClickListener {

    private static final int PICK_CONTACT_REQUEST_CODE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 11;

    private Toolbar toolbar;
    private RecyclerView rvWhitelistContacts;
    private RecyclerViewWhitelistContactAdapter rvWhitelistContactAdapter;
    private FloatingActionButton btnAddWhitelistContact;

    private WhitelistContactContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whitelist_contacts);

        presenter = new WhitelistContactPresenterImpl(getApplicationContext(), this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvWhitelistContacts = findViewById(R.id.rvWhitelistContacts);
        rvWhitelistContacts.setLayoutManager(new LinearLayoutManager(WhitelistContactsActivity.this));

        btnAddWhitelistContact = findViewById(R.id.fabAddWhitelistContact);
        btnAddWhitelistContact.setOnClickListener(this);

        if(!readContactPermissionGranted()){
            ActivityCompat.requestPermissions(WhitelistContactsActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }

        presenter.loadWhitelistContacts();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void loadWhitelistContacts(List<WhitelistContact> whitelistContactList) {
        rvWhitelistContactAdapter = new RecyclerViewWhitelistContactAdapter(whitelistContactList);
        rvWhitelistContacts.setAdapter(rvWhitelistContactAdapter);
        rvWhitelistContactAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabAddWhitelistContact:
                if(readContactPermissionGranted()) {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityIfNeeded(intent, PICK_CONTACT_REQUEST_CODE);
                }
                break;
        }
    }

    private Boolean readContactPermissionGranted(){
        return ContextCompat.checkSelfPermission(WhitelistContactsActivity.this,
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_CONTACT_REQUEST_CODE:
                if (resultCode == RESULT_OK){
                    WhitelistContact selectedContactData = getSelectedContactData(data);
                    if(selectedContactData!=null){
                        presenter.saveWhitelistContact(selectedContactData);
                        presenter.loadWhitelistContacts();
                    }
                }
                break;
        }
    }

    private WhitelistContact getSelectedContactData(Intent data){
        Uri contactData = data.getData();
        Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
        if (cursor.moveToFirst()) {
            int hasPhoneIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            if (cursor.getString(hasPhoneIndex).equals("1")) {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor contact = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null, null
                );
                contact.moveToFirst();
                String contactDisplayName = contact.getString(contact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactPhoneNumber = contact.getString(contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                return WhitelistContact.builder()
                        .id(contactId)
                        .name(contactDisplayName)
                        .phoneNumber(contactPhoneNumber)
                        .build();
            }
        }
        cursor.close();
        return null;
    }
}
