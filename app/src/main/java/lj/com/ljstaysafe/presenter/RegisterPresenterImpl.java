package lj.com.ljstaysafe.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.RegisterContract;
import lj.com.ljstaysafe.model.Friend;
import lj.com.ljstaysafe.model.User;

public class RegisterPresenterImpl implements RegisterContract.Presenter {

    private Context context;
    private RegisterContract.View view;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;

    public RegisterPresenterImpl(Context context, RegisterContract.View view) {
        this.context = context;
        this.view = view;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection(context.getResources().getString(R.string.user_collection));
    }

    @Override
    public void saveUser(final User user, String userPassword) {
        view.showLoadingView();
        saveUserToFirebaseAuth(user, userPassword);
    }

    private void saveUserToFirebaseAuth(final User user, String userPassword){
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.setId(task.getResult().getUser().getUid());
                            user.setFriendList(new ArrayList<Friend>()); //default empty array (no friends yet)
                            saveUserToFirestore(user);
                        } else {
                            Toast.makeText(context, "Error while registering!\n" +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            view.dismissLoadingView();
                        }
                    }
                });
    }

    private void saveUserToFirestore(final User user){
        collectionReference.document(user.getId()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, user.getEmail() + " successfully registered!", Toast.LENGTH_SHORT).show();
                    view.dismissLoadingView();
                    view.moveToLoginPage();
                }
            }
        });
    }

}
