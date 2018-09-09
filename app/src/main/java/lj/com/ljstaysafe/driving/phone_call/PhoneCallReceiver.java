package lj.com.ljstaysafe.driving.phone_call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;

import lj.com.ljstaysafe.contract.DrivingStatusContract;
import lj.com.ljstaysafe.contract.NotificationSettingsDialogContract;
import lj.com.ljstaysafe.model.WhitelistContact;
import lj.com.ljstaysafe.repository.AppDatabase;
import lj.com.ljstaysafe.repository.audio.AudioRepository;
import lj.com.ljstaysafe.repository.audio.AudioRepositoryImpl;
import lj.com.ljstaysafe.repository.driving.DrivingStatusRepositoryImpl;
import lj.com.ljstaysafe.repository.notification.NotificationSettingRepositoryImpl;
import lj.com.ljstaysafe.repository.whitelist_contact.WhitelistContactRepository;

public class PhoneCallReceiver extends BroadcastReceiver {

    private static final String TAG = PhoneCallReceiver.class.getName();

    private DrivingStatusContract.Repository drivingStatusRepository;
    private NotificationSettingsDialogContract.Repository notificationSettingsRepository;
    private AudioManager audioManager;
    private AudioRepository audioRepository;

    private AppDatabase database;

    public PhoneCallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        database = AppDatabase.getDbInstance(context);
        drivingStatusRepository = new DrivingStatusRepositoryImpl(context);
        notificationSettingsRepository = new NotificationSettingRepositoryImpl(context);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioRepository = new AudioRepositoryImpl(context);

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        if (drivingStatusRepository.isDriving() && notificationSettingsRepository.getSetting() && state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            new LoadWhitelistContactsAsync(database.whitelistContactRepository(), audioManager, audioRepository, incomingNumber).execute();
        }
        if (drivingStatusRepository.isDriving() && notificationSettingsRepository.getSetting() && state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        if (drivingStatusRepository.isDriving() && notificationSettingsRepository.getSetting() && state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }

    private static class LoadWhitelistContactsAsync extends AsyncTask<Void, Void, List<WhitelistContact>> {

        private WhitelistContactRepository whitelistContactRepository;
        private AudioManager audioManager;
        private AudioRepository audioRepository;
        private String incomingNumber;

        public LoadWhitelistContactsAsync(
                WhitelistContactRepository whitelistContactRepository,
                AudioManager audioManager,
                AudioRepository audioRepository,
                String incomingNumber) {
            this.whitelistContactRepository = whitelistContactRepository;
            this.audioManager = audioManager;
            this.audioRepository = audioRepository;
            this.incomingNumber = incomingNumber;
        }

        @Override
        protected List<WhitelistContact> doInBackground(Void... voids) {
            return whitelistContactRepository.findAllWhitelistContact();
        }

        @Override
        protected void onPostExecute(List<WhitelistContact> whitelistContacts) {
            super.onPostExecute(whitelistContacts);
            for (WhitelistContact whitelistContact:whitelistContacts){
                Log.i(TAG, whitelistContact.getName() + " | " + whitelistContact.getPhoneNumber());
                String whiteListContact0 = whitelistContact.getPhoneNumber();
                String whiteListContact62 = whiteListContact0.replaceFirst("0", "+62");
                if(incomingNumber.equals(whiteListContact0) || incomingNumber.equals(whiteListContact62)){
                    audioManager.setRingerMode(audioRepository.getSavedRingerMode());
                }
            }
        }
    }
}
