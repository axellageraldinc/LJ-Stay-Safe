package lj.com.ljstaysafe.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.HomeContract;
import lj.com.ljstaysafe.model.Feed;
import lj.com.ljstaysafe.model.Friend;

public class HomePresenterImpl implements HomeContract.Presenter {

    private Context context;
    private HomeContract.View view;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    List<Friend> userFriends = new ArrayList<>();
    List<Feed> feeds = new ArrayList<>();

    public HomePresenterImpl(Context context, HomeContract.View view) {
        this.context = context;
        this.view = view;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void loadFeeds() {
        view.showLoadingView();
        final String userId = firebaseUser.getUid();
        firebaseFirestore
                .collection(context.getResources().getString(R.string.friend_collection))
                .whereEqualTo("friendId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                Friend friend = documentSnapshot.toObject(Friend.class);
                                userFriends.add(friend);
                            }
                            // Add the current user to friend list because we also want to load his/her own feeds
                            userFriends.add(Friend.builder()
                                    .userId(userId)
                                    .build());
                            // THIS IS BAD!
                            for (Friend friend : userFriends) {
                                firebaseFirestore
                                        .collection(context.getResources().getString(R.string.feed_collection))
                                        .whereEqualTo("ownerId", friend.getUserId())
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                feeds.clear();
                                                for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(queryDocumentSnapshots).getDocuments()) {
                                                    Feed feed = documentSnapshot.toObject(Feed.class);
                                                    feeds.add(feed);
                                                }
                                                view.showFeeds(feeds);
                                                view.dismissLoadingView();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(context, "Failed loading data\n" +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            view.dismissLoadingView();
                        }
                    }
                });
    }
}
