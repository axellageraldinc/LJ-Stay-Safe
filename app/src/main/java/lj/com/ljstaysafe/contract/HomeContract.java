package lj.com.ljstaysafe.contract;

import java.util.List;

import lj.com.ljstaysafe.model.Feed;

public interface HomeContract {
    interface View{
        void showLoadingView();
        void dismissLoadingView();
        void showFeeds(List<Feed> feeds);
    }
    interface Presenter{
        void loadFeeds();
    }
}
