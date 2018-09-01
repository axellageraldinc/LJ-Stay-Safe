package lj.com.ljstaysafe.contract;

public interface LoginContract {
    interface View{
        void showLoadingView();
        void dismissLoadingView();
        void moveToHomepage();
    }
    interface Repository{
        void saveUserLoginInfo(String email);
        boolean isUserAlreadyLoggedIn();
        String findUserEmail();
        void removeUserLoginInfo();
    }
    interface Presenter{
        boolean isUserAlreadyLoggedIn();
        void login(String email, String password);
    }
}
