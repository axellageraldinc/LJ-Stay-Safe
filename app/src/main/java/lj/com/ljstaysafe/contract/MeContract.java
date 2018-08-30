package lj.com.ljstaysafe.contract;

public interface MeContract {
    interface View{
        void setUserFullname(String fullname);
        void setLjPoints(String ljPoints);
        void showLoadingView();
        void dismissLoadingView();
        void moveToLoginPage();
    }
    interface Presenter{
        void loadUserProfile();
        void logout();
    }
}
