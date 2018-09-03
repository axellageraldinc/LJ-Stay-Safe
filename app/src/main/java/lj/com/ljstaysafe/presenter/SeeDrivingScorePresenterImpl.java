package lj.com.ljstaysafe.presenter;

import android.content.Context;
import java.util.Objects;

import lj.com.ljstaysafe.contract.SeeDrivingScoreContract;
import lj.com.ljstaysafe.model.DrivingHistory;
import lj.com.ljstaysafe.model.ShareDrivingScoreDestination;
import lj.com.ljstaysafe.service.ShareDrivingScoreFactory;
import lj.com.ljstaysafe.service.ShareDrivingScoreService;

public class SeeDrivingScorePresenterImpl implements SeeDrivingScoreContract.Presenter {

    private static final String TAG = SeeDrivingScorePresenterImpl.class.getName();

    private SeeDrivingScoreContract.View view;
    private Context context;

    public SeeDrivingScorePresenterImpl(SeeDrivingScoreContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void shareDrivingScore(DrivingHistory drivingHistory, ShareDrivingScoreDestination shareDrivingScoreDestination) {
        ShareDrivingScoreService shareDrivingScoreService = ShareDrivingScoreFactory.getShareDrivingScoreService(view, context, shareDrivingScoreDestination);
        Objects.requireNonNull(shareDrivingScoreService).shareDrivingScore(drivingHistory);
    }

}
