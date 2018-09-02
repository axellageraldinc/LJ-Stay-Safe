package lj.com.ljstaysafe.driving.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import lj.com.ljstaysafe.contract.DrivingStatusContract;
import lj.com.ljstaysafe.contract.MessageSettingsDialogContract;
import lj.com.ljstaysafe.repository.driving.DrivingStatusRepositoryImpl;
import lj.com.ljstaysafe.repository.message.MessageSettingRepositoryImpl;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = SmsReceiver.class.getName();
    private Context context;
    private DrivingStatusContract.Repository drivingStatusRepository;
    private MessageSettingsDialogContract.Repository messageSettingsRepository;

    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        drivingStatusRepository = new DrivingStatusRepositoryImpl(context);
        messageSettingsRepository = new MessageSettingRepositoryImpl(context);
        if (drivingStatusRepository.isDriving() && messageSettingsRepository.getSetting().getIsAutoReplySms()){
            String incomingPhoneNumber = getIncomingPhoneNumber(intent);
            String quickReplyMessage = messageSettingsRepository.getSetting().getMessage();
            SmsManager smsManager = SmsManager.getDefault();
            try{
                smsManager.sendTextMessage(incomingPhoneNumber, null, quickReplyMessage, null, null);
                Log.i(TAG, "Text Message :\n" +
                        "Recipient : " + incomingPhoneNumber + "\n" +
                        "Content : " + quickReplyMessage + "\n" +
                        "Sent successfully!");
            } catch (Exception ex){
                Log.e(TAG, "Error send sms : " + ex.getMessage());
            }
        }
    }

    private String getIncomingPhoneNumber(Intent intent){
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String incomingSmsPhoneNumber = "";

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            // For every SMS message received
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                incomingSmsPhoneNumber = msgs[i].getOriginatingAddress();
            }
        }
        return incomingSmsPhoneNumber;
    }
}
