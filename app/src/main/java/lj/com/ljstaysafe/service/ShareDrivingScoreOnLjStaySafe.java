package lj.com.ljstaysafe.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.UUID;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.SeeDrivingScoreContract;
import lj.com.ljstaysafe.model.DrivingHistory;
import lj.com.ljstaysafe.model.Feed;
import lj.com.ljstaysafe.model.User;

public class ShareDrivingScoreOnLjStaySafe implements ShareDrivingScoreService {

    private SeeDrivingScoreContract.View view;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    public ShareDrivingScoreOnLjStaySafe(SeeDrivingScoreContract.View view, Context context) {
        this.view = view;
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void shareDrivingScore(final DrivingHistory drivingHistory) {
        view.showLoadingView();
        final String userId = firebaseUser.getUid();
        firebaseFirestore
                .collection(context.getResources().getString(R.string.user_collection))
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            User user = task.getResult().toObject(User.class);
                            Feed feed = Feed.builder()
                                    .id(UUID.randomUUID().toString())
                                    .ownerId(userId)
                                    .ownerFullname(user.getFullname())
                                    .content("I just finished driving with score " + drivingHistory.getOverallScore() + "/10")
                                    .publishedDate(new Date())
                                    .build();
                            firebaseFirestore
                                    .collection(context.getResources().getString(R.string.feed_collection))
                                    .add(feed)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(context, "Your driving score has been shared successfully\n" +
                                                        "Keep up the work!", Toast.LENGTH_SHORT).show();
                                                view.dismissLoadingView();
                                            } else{
                                                Toast.makeText(context, "Failed sharing driving score...\n" +
                                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                view.dismissLoadingView();
                                            }
                                        }
                                    });
                        } else{
                            Toast.makeText(context, "Failed loading user data...\n" +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            view.dismissLoadingView();
                        }
                    }
                });
    }
}
