package lj.com.ljstaysafe.contract;

import lj.com.ljstaysafe.model.DrivingHistory;
import lj.com.ljstaysafe.model.ShareDrivingScoreDestination;

public interface SeeDrivingScoreContract {
    interface View{
        void showLoadingView();
        void dismissLoadingView();
    }
    interface Presenter{
        void shareDrivingScore(DrivingHistory drivingHistory, ShareDrivingScoreDestination shareDrivingScoreDestination);
    }
}
