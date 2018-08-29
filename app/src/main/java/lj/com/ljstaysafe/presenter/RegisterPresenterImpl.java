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

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.RegisterContract;
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
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user.setId(task.getResult().getUser().getUid());
                            collectionReference.add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(context, user.getEmail() + " successfully registered!", Toast.LENGTH_SHORT).show();
                                        view.dismissLoadingView();
                                    }
                                }
                            });
                        } else{
                            Toast.makeText(context, "Error while registering!\n" +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            view.dismissLoadingView();
                        }
                    }
                });
    }
}
