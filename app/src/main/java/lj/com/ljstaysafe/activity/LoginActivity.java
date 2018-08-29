package lj.com.ljstaysafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lj.com.ljstaysafe.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvSignupHere;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        tvSignupHere = findViewById(R.id.tvSignUpHere);
        tvSignupHere.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.tvSignUpHere:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
        }
    }

}
