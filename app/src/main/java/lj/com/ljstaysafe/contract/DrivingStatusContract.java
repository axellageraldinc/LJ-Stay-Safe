package lj.com.ljstaysafe.contract;

public interface DrivingStatusContract {
    interface Interactor{
        void saveDrivingStatus(Boolean isDriving);
        Boolean getDrivingStatus(Boolean isDriving);
    }
}
