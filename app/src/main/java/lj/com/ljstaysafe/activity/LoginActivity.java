package lj.com.ljstaysafe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.LoginContract;
import lj.com.ljstaysafe.presenter.LoginPresenterImpl;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginContract.View{

    private TextView tvSignupHere;
    private EditText etEmail, etPassword;
    private Button btnLogin;

    private ProgressDialog progressDialog;

    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        tvSignupHere = findViewById(R.id.tvSignUpHere);
        tvSignupHere.setOnClickListener(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Logging in...");
        progressDialog.setIndeterminate(true);

        presenter = new LoginPresenterImpl(LoginActivity.this, this);

        checkIfUserAlreadyLoggedIn();
    }

    private void checkIfUserAlreadyLoggedIn(){
        boolean isUserAlreadyLoggedIn = presenter.isUserAlreadyLoggedIn();
        if(isUserAlreadyLoggedIn){
            moveToHomepage();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                presenter.login(etEmail.getText().toString(), etPassword.getText().toString());
                break;
            case R.id.tvSignUpHere:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
        }
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
    public void moveToHomepage() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
