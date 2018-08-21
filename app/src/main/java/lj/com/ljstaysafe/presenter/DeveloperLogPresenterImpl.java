package lj.com.ljstaysafe.presenter;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import lj.com.ljstaysafe.contract.DeveloperLogContract;
import lj.com.ljstaysafe.model.DeveloperLog;
import lj.com.ljstaysafe.repository.AppDatabase;
import lj.com.ljstaysafe.repository.developer.DeveloperLogRepository;

public class DeveloperLogPresenterImpl implements DeveloperLogContract.Presenter {

    private Context context;
    private DeveloperLogContract.View view;
    private AppDatabase developerLogDatabase;

    public DeveloperLogPresenterImpl(Context context, DeveloperLogContract.View view) {
        this.context = context;
        this.view = view;
        developerLogDatabase = AppDatabase.getDbInstance(context);
    }

    @Override
    public void saveLog(DeveloperLog developerLog) {
        new SaveLog(developerLogDatabase.developerLogRepository(), view).execute(developerLog);
    }

    @Override
    public void loadLogs() {
        new LoadLogs(developerLogDatabase.developerLogRepository(), view).execute();
    }

    private static class SaveLog extends AsyncTask<DeveloperLog, Void, Void> {

        private DeveloperLogRepository developerLogRepository;
        private DeveloperLogContract.View view;

        public SaveLog(DeveloperLogRepository developerLogRepository, DeveloperLogContract.View view) {
            this.developerLogRepository = developerLogRepository;
            this.view = view;
        }

        @Override
        protected Void doInBackground(DeveloperLog... developerLogs) {
            developerLogRepository.saveLog(developerLogs[0]);
            return null;
        }
    }

    private static class LoadLogs extends AsyncTask<Void, Void, List<DeveloperLog>>{

        private DeveloperLogRepository developerLogRepository;
        private DeveloperLogContract.View view;

        public LoadLogs(DeveloperLogRepository developerLogRepository, DeveloperLogContract.View view) {
            this.developerLogRepository = developerLogRepository;
            this.view = view;
        }

        @Override
        protected List<DeveloperLog> doInBackground(Void... voids) {
            return developerLogRepository.findAllLog();
        }

        @Override
        protected void onPostExecute(List<DeveloperLog> developerLogList) {
            super.onPostExecute(developerLogList);
            view.loadDeveloperLog(developerLogList);
        }
    }
}
