package lj.com.ljstaysafe.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.LoginContract;
import lj.com.ljstaysafe.contract.MeContract;
import lj.com.ljstaysafe.model.User;
import lj.com.ljstaysafe.repository.user.LoginRepositoryImpl;

public class MePresenterImpl implements MeContract.Presenter {

    private MeContract.View view;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    private LoginContract.Repository repository;

    public MePresenterImpl(MeContract.View view, Context context) {
        this.view = view;
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        repository = new LoginRepositoryImpl(context);
    }

    @Override
    public void loadUserProfile() {
        view.showLoadingView();
        String userId = firebaseUser.getUid();
        firebaseFirestore
                .collection(context.getResources().getString(R.string.user_collection))
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            User user = task.getResult().toObject(User.class);
                            view.setUserFullname(Objects.requireNonNull(user).getFullname());
                            view.setLjPoints(String.valueOf(user.getPoints()));
                            view.dismissLoadingView();
                        } else{
                            Toast.makeText(context, "Failed loading data\n" +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            view.dismissLoadingView();
                        }
                    }
                });
    }

    @Override
    public void logout() {
        firebaseAuth.signOut();
        repository.removeUserLoginInfo();
        view.moveToLoginPage();
    }
}
