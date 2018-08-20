package lj.com.ljstaysafe.contract;

import java.util.List;

import lj.com.ljstaysafe.model.WhitelistContact;

public interface WhitelistContactContract {
    interface View{
        void loadWhitelistContacts(List<WhitelistContact> whitelistContactList);
    }
    interface Presenter{
        void loadWhitelistContacts();
        void saveWhitelistContact(WhitelistContact whitelistContact);
        void updateWhitelistContact(WhitelistContact whitelistContact);
        void deleteWhitelistContact(WhitelistContact whitelistContact);
    }
}
