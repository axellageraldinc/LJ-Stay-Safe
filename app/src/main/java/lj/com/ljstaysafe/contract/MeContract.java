package lj.com.ljstaysafe.contract;

public interface MeContract {
    interface View{
        void setUserFullname(String fullname);
        void setLjPoints(String ljPoints);
        void showLoadingView();
        void dismissLoadingView();
    }
    interface Presenter{
        void loadUserProfile();
    }
}
