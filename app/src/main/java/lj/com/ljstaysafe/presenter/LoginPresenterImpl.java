package lj.com.ljstaysafe.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import lj.com.ljstaysafe.contract.LoginContract;
import lj.com.ljstaysafe.repository.user.LoginRepositoryImpl;

public class LoginPresenterImpl implements LoginContract.Presenter {

    private Context context;
    private LoginContract.View view;
    private LoginContract.Repository repository;
    private FirebaseAuth firebaseAuth;

    public LoginPresenterImpl(Context context, LoginContract.View view) {
        this.context = context;
        this.view = view;
        repository = new LoginRepositoryImpl(context);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean isUserAlreadyLoggedIn() {
        return repository.isUserAlreadyLoggedIn();
    }

    @Override
    public void login(final String email, String password) {
        view.showLoadingView();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context, "Hi, " + email + "!", Toast.LENGTH_SHORT).show();
                            repository.saveUserLoginInfo(email);
                            view.dismissLoadingView();
                            view.moveToHomepage();
                        } else{
                            Toast.makeText(context, "Login failed\n" +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            view.dismissLoadingView();
                        }
                    }
                });
    }
}
