package lj.com.ljstaysafe.presenter;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import lj.com.ljstaysafe.contract.DrivingHistoryContract;
import lj.com.ljstaysafe.model.DrivingHistory;
import lj.com.ljstaysafe.repository.AppDatabase;
import lj.com.ljstaysafe.repository.driving_history.DrivingHistoryRepository;

public class DrivingHistoryPresenterImpl implements DrivingHistoryContract.Presenter {

    private Context context;
    private DrivingHistoryContract.View view;
    private AppDatabase database;

    public DrivingHistoryPresenterImpl(Context context, DrivingHistoryContract.View view) {
        this.context = context;
        this.view = view;
        database = AppDatabase.getDbInstance(context);
    }

    @Override
    public void loadDrivingHistories() {
        new LoadDrivingHistories(database.drivingHistoryRepository(), view).execute();
    }

    private static class LoadDrivingHistories extends AsyncTask<Void, Void, List<DrivingHistory>> {

        private DrivingHistoryRepository drivingHistoryRepository;
        private DrivingHistoryContract.View view;

        public LoadDrivingHistories(DrivingHistoryRepository drivingHistoryRepository, DrivingHistoryContract.View view) {
            this.drivingHistoryRepository = drivingHistoryRepository;
            this.view = view;
        }

        @Override
        protected List<DrivingHistory> doInBackground(Void... voids) {
            return drivingHistoryRepository.findAllDrivingHistory();
        }

        @Override
        protected void onPostExecute(List<DrivingHistory> drivingHistoryList) {
            super.onPostExecute(drivingHistoryList);
            view.loadDrivingHistories(drivingHistoryList);
        }
    }

}
