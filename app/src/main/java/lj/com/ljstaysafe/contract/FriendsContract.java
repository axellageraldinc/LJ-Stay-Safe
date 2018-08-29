package lj.com.ljstaysafe.contract;

import java.util.List;

import lj.com.ljstaysafe.model.Friend;

public interface FriendsContract {
    interface View{
        void showLoadingView();
        void dismissLoadingView();
        void showFriends(List<Friend> friends);
    }
    interface Presenter{
        void loadFriends();
        void saveNewFriend(String newFriendEmail);
    }
}
