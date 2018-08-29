package lj.com.ljstaysafe.contract;

import lj.com.ljstaysafe.model.User;

public interface RegisterContract {
    interface View{
        void showLoadingView();
        void dismissLoadingView();
    }
    interface Presenter{
        void saveUser(User user, String userPassword);
    }
}
