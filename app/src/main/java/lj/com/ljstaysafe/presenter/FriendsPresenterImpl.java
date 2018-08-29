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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.FriendsContract;
import lj.com.ljstaysafe.model.Friend;
import lj.com.ljstaysafe.model.User;

public class FriendsPresenterImpl implements FriendsContract.Presenter {

    private Context context;
    private FriendsContract.View view;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    private List<Friend> friends = new ArrayList<>();

    public FriendsPresenterImpl(Context context, FriendsContract.View view) {
        this.context = context;
        this.view = view;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void loadFriends() {
        view.showLoadingView();
        String userId = firebaseUser.getUid();
        firebaseFirestore
                .collection(context.getResources().getString(R.string.user_collection))
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            User user = task.getResult().toObject(User.class);
                            friends.clear();
                            friends.addAll(Objects.requireNonNull(user).getFriendList());
                            view.showFriends(friends);
                            view.dismissLoadingView();
                        } else {
                            Toast.makeText(context, "Loading friends failed\n" +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            view.dismissLoadingView();
                        }
                    }
                });
    }

    @Override
    public void saveNewFriend(final String newFriendEmail) {
        view.showLoadingView();
        final String userId = firebaseUser.getUid();
        firebaseFirestore
                .collection(context.getResources().getString(R.string.user_collection))
                .whereEqualTo("email", newFriendEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                User newFriendAccount = documentSnapshot.toObject(User.class);
                                friends.add(Friend.builder()
                                        .userId(Objects.requireNonNull(newFriendAccount).getId())
                                        .userFullname(newFriendAccount.getFullname())
                                        .build());
                                firebaseFirestore
                                        .collection(context.getResources().getString(R.string.user_collection))
                                        .document(userId)
                                        .update("friendList", friends)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    view.showFriends(friends);
                                                    view.dismissLoadingView();
                                                } else {
                                                    Toast.makeText(context, "Adding new friend failed\n" +
                                                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    view.dismissLoadingView();
                                                }
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(context, "Searching user with email : " + newFriendEmail + " failed\n" +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            view.dismissLoadingView();
                        }
                    }
                });
    }
}
