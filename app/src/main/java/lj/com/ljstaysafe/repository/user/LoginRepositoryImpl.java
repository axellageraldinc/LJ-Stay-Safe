package lj.com.ljstaysafe.repository.user;

import android.content.Context;
import android.content.SharedPreferences;

import lj.com.ljstaysafe.contract.LoginContract;

public class LoginRepositoryImpl implements LoginContract.Repository {

    private Context context;
    private SharedPreferences sharedPreferences;

    public LoginRepositoryImpl(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(LoginRepositoryImpl.class.getName(), Context.MODE_PRIVATE);
    }

    @Override
    public void saveUserLoginInfo(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userEmail", email);
        editor.apply();
    }

    @Override
    public boolean isUserAlreadyLoggedIn() {
        return sharedPreferences.contains("userEmail");
    }

    @Override
    public String findUserEmail() {
        return sharedPreferences.getString("userEmail", "");
    }

    @Override
    public void removeUserLoginInfo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userEmail");
        editor.apply();
    }

}
