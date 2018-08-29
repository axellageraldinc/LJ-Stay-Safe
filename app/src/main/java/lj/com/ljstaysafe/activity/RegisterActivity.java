package lj.com.ljstaysafe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.RegisterContract;
import lj.com.ljstaysafe.model.User;
import lj.com.ljstaysafe.presenter.RegisterPresenterImpl;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View, Button.OnClickListener {

    private EditText etEmail, etPassword, etFullname;
    private Button btnRegister;

    private ProgressDialog progressDialog;

    private RegisterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");

        presenter = new RegisterPresenterImpl(this, RegisterActivity.this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etFullname = findViewById(R.id.etFullname);
        etFullname.requestFocus();

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void showLoadingView() {
        progressDialog.show();
    }

    @Override
    public void dismissLoadingView() {
        progressDialog.dismiss();
    }

    @Override
    public void moveToLoginPage() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

    private User buildUser(){
        return User.builder()
                .email(etEmail.getText().toString())
                .fullname(etFullname.getText().toString())
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                User userToRegister = buildUser();
                presenter.saveUser(userToRegister, etPassword.getText().toString());
                break;
        }
    }
}
