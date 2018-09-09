package lj.com.ljstaysafe.driving;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceState;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.contract.DrivingStatusContract;
import lj.com.ljstaysafe.contract.NotificationSettingsDialogContract;
import lj.com.ljstaysafe.model.BehaviourScoringHelper;
import lj.com.ljstaysafe.model.DrivingHistory;
import lj.com.ljstaysafe.repository.AppDatabase;
import lj.com.ljstaysafe.repository.audio.AudioRepository;
import lj.com.ljstaysafe.repository.audio.AudioRepositoryImpl;
import lj.com.ljstaysafe.repository.driving.DrivingStatusRepositoryImpl;
import lj.com.ljstaysafe.repository.driving_history.DrivingHistoryRepository;
import lj.com.ljstaysafe.repository.notification.NotificationSettingRepositoryImpl;

public class CheckDrivingStatusBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = CheckDrivingStatusBroadcastReceiver.class.getSimpleName();

    private DrivingStatusContract.Repository drivingStatusRepository;
    private NotificationSettingsDialogContract.Repository notificationSettingsRepository;
    private AudioManager audioManager;
    private AudioRepository audioRepository;
    private NotificationHandler notificationHandler;
    private Context context;
    private AppDatabase database;
    private DecimalFormat df = new DecimalFormat("#.##");

    public CheckDrivingStatusBroadcastReceiver(Context context) {
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioRepository = new AudioRepositoryImpl(context);
        drivingStatusRepository = new DrivingStatusRepositoryImpl(context);
        notificationHandler = new NotificationHandler(context);
        database = AppDatabase.getDbInstance(context);
        notificationSettingsRepository = new NotificationSettingRepositoryImpl(context);
    }

    public CheckDrivingStatusBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        FenceState fenceState = FenceState.extract(intent);
        Log.d(TAG, "Fence Receiver Received");
        if (fenceState.getFenceKey().equals(context.getResources().getString(R.string.driving_status))) {
            switch (fenceState.getCurrentState()) {
                case FenceState.TRUE:
                    if (!drivingStatusRepository.isPassenger()) {
                        drivingStatusRepository.saveDrivingStatus(true);
                        notificationHandler.createNotification(
                                true,
                                "No phone while driving!!!",
                                "Click here if you are a passenger",
                                true);
                        if (notificationSettingsRepository.getSetting()) {
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        }
                    }
                    break;
                case FenceState.FALSE:
                    if (!drivingStatusRepository.isPassenger()) {
                        drivingStatusRepository.saveDrivingStatus(false);
                        DrivingHistory drivingHistory = DrivingHistory.builder()
                                .id(UUID.randomUUID().toString())
                                .distractedScore(df.format(BehaviourScoringHelper.CURRENT_DISTRACTED_SCORE))
                                .honkingScore(df.format(0.0))
                                .highSpeedScore(df.format(0.0))
                                .suddenBrakeScore(df.format(0.0))
                                .turnScore(df.format(0.0))
                                .overallScore(df.format(BehaviourScoringHelper.CURRENT_OVERALL_SCORE))
                                .createdDate(new Date())
                                .build();
                        new SaveDrivingHistory(
                                database.drivingHistoryRepository())
                                .execute(drivingHistory);
                        resetBehaviourScore();
                        notificationHandler.createNotification(
                                "Driving is over",
                                "Click here to see your driving score",
                                false, drivingHistory);
                        if (notificationSettingsRepository.getSetting())
                            audioManager.setRingerMode(audioRepository.getSavedRingerMode());
                    } else {
                        drivingStatusRepository.savePassengerStatus(false);
                        notificationHandler.createNotification(
                                false,
                                "Driving is over",
                                "Click here to see your driving score",
                                false);
                    }
                    break;
                case FenceState.UNKNOWN:
                    if (!drivingStatusRepository.isPassenger()) {
                        drivingStatusRepository.saveDrivingStatus(false);
                        if (notificationSettingsRepository.getSetting())
                            audioManager.setRingerMode(audioRepository.getSavedRingerMode());
                    } else {
                        drivingStatusRepository.savePassengerStatus(false);
                    }
                    break;
            }
        }
    }

    private static class SaveDrivingHistory extends AsyncTask<DrivingHistory, Void, Void> {

        private DrivingHistoryRepository drivingHistoryRepository;

        public SaveDrivingHistory(DrivingHistoryRepository drivingHistoryRepository) {
            this.drivingHistoryRepository = drivingHistoryRepository;
        }

        @Override
        protected Void doInBackground(DrivingHistory... drivingHistories) {
            drivingHistoryRepository.saveDrivingHistory(drivingHistories[0]);
            return null;
        }
    }

    private void resetBehaviourScore(){
        BehaviourScoringHelper.CURRENT_DISTRACTED_SCORE = BehaviourScoringHelper.INITIAL_DISTRACTED_SCORE;
        BehaviourScoringHelper.CURRENT_OVERALL_SCORE = BehaviourScoringHelper.INITIAL_OVERALL_SCORE;
    }

}
