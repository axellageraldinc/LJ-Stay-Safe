package lj.com.ljstaysafe.contract;

import java.util.List;

import lj.com.ljstaysafe.model.DrivingHistory;

public interface DrivingHistoryContract {
    interface View{
        void loadDrivingHistories(List<DrivingHistory> drivingHistoryList);
    }
    interface Presenter{
        void loadDrivingHistories();
    }
}
