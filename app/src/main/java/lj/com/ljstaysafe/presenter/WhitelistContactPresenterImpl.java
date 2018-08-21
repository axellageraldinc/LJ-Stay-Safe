package lj.com.ljstaysafe.presenter;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import lj.com.ljstaysafe.contract.WhitelistContactContract;
import lj.com.ljstaysafe.model.WhitelistContact;
import lj.com.ljstaysafe.repository.AppDatabase;
import lj.com.ljstaysafe.repository.whitelist_contact.WhitelistContactRepository;

public class WhitelistContactPresenterImpl implements WhitelistContactContract.Presenter {

    private AppDatabase database;
    private Context context;
    private WhitelistContactContract.View view;

    public WhitelistContactPresenterImpl(Context context, WhitelistContactContract.View view) {
        this.context = context;
        database = AppDatabase.getDbInstance(context);
        this.view = view;
    }

    @Override
    public void loadWhitelistContacts() {
        new LoadWhitelistContactsAsync(database.whitelistContactRepository(), view).execute();
    }

    @Override
    public void saveWhitelistContact(WhitelistContact whitelistContact) {
        new InsertWhiteListContactAsync(database.whitelistContactRepository(), view).execute(whitelistContact);
    }

    @Override
    public void updateWhitelistContact(WhitelistContact whitelistContact) {
        new UpdateWhitelistContact(database.whitelistContactRepository(), view).execute(whitelistContact);
    }

    @Override
    public void deleteWhitelistContact(WhitelistContact whitelistContact) {
        new DeleteWhitelistContact(database.whitelistContactRepository(), view).execute(whitelistContact);
    }

    private static class InsertWhiteListContactAsync extends AsyncTask<WhitelistContact, Void, Void>{

        private WhitelistContactRepository whitelistContactRepository;
        private WhitelistContactContract.View view;

        public InsertWhiteListContactAsync(WhitelistContactRepository whitelistContactRepository, WhitelistContactContract.View view) {
            this.whitelistContactRepository = whitelistContactRepository;
            this.view = view;
        }

        @Override
        protected Void doInBackground(WhitelistContact... whitelistContacts) {
            whitelistContactRepository.insertWhitelistContact(whitelistContacts[0]);
            return null;
        }
    }

    private static class UpdateWhitelistContact extends AsyncTask<WhitelistContact, Void, Void>{

        private WhitelistContactRepository whitelistContactRepository;
        private WhitelistContactContract.View view;

        public UpdateWhitelistContact(WhitelistContactRepository whitelistContactRepository, WhitelistContactContract.View view) {
            this.whitelistContactRepository = whitelistContactRepository;
            this.view = view;
        }

        @Override
        protected Void doInBackground(WhitelistContact... whitelistContacts) {
            whitelistContactRepository.updateWhitelistContact(whitelistContacts[0]);
            return null;
        }
    }

    private static class DeleteWhitelistContact extends AsyncTask<WhitelistContact, Void, Void>{

        private WhitelistContactRepository whitelistContactRepository;
        private WhitelistContactContract.View view;

        public DeleteWhitelistContact(WhitelistContactRepository whitelistContactRepository, WhitelistContactContract.View view) {
            this.whitelistContactRepository = whitelistContactRepository;
            this.view = view;
        }

        @Override
        protected Void doInBackground(WhitelistContact... whitelistContacts) {
            whitelistContactRepository.deleteWhitelistContact(whitelistContacts[0]);
            return null;
        }
    }

    private static class LoadWhitelistContactsAsync extends AsyncTask<Void, Void, List<WhitelistContact>>{

        private WhitelistContactRepository whitelistContactRepository;
        private WhitelistContactContract.View view;

        public LoadWhitelistContactsAsync(WhitelistContactRepository whitelistContactRepository, WhitelistContactContract.View view) {
            this.whitelistContactRepository = whitelistContactRepository;
            this.view = view;
        }

        @Override
        protected List<WhitelistContact> doInBackground(Void... voids) {
            return whitelistContactRepository.findAllWhitelistContact();
        }

        @Override
        protected void onPostExecute(List<WhitelistContact> whitelistContacts) {
            super.onPostExecute(whitelistContacts);
            view.loadWhitelistContacts(whitelistContacts);
        }
    }
}
