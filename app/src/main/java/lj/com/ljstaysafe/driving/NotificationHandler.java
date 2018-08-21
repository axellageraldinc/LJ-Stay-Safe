package lj.com.ljstaysafe.driving;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.activity.PassengerActivity;
import lj.com.ljstaysafe.activity.SeeDrivingScoreActivity;

public class NotificationHandler {

    private static final String CHANNEL_ID = "123";
    private static final Integer NOTIFICATION_ID = 123;

    private Context context;
    private NotificationManagerCompat notificationManager;

    private Intent intent;
    private PendingIntent pendingIntent;

    public NotificationHandler(Context context) {
        this.context = context;
        notificationManager = NotificationManagerCompat.from(context);
    }

    public void createNotification(Boolean isDriving, String title, String content, Boolean isPersistentNotification){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_car)
                .setContentTitle(title)
                .setContentText(content)
                .setOngoing(isPersistentNotification)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        if(isDriving){
            intent = new Intent(context, PassengerActivity.class);
        } else{
            intent = new Intent(context, SeeDrivingScoreActivity.class);
            notificationBuilder.setAutoCancel(true);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
