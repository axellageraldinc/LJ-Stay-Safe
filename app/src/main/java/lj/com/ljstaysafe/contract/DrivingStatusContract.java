package lj.com.ljstaysafe.contract;

public interface DrivingStatusContract {
    interface Interactor{
        void saveDrivingStatus(Boolean isDriving);
        void savePassengerStatus(Boolean isPassenger);
        Boolean isDriving();
        Boolean isPassenger();
    }
}
