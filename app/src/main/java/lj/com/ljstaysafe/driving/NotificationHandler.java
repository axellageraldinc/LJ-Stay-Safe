package lj.com.ljstaysafe.driving;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import lj.com.ljstaysafe.R;
import lj.com.ljstaysafe.activity.PassengerActivity;
import lj.com.ljstaysafe.activity.SeeDrivingScoreActivity;

public class NotificationHandler {

    private static final String CHANNEL_ID = "123";
    private static final int NOTIFICATION_ID = 123;

    private Context context;
    private NotificationManager notificationManager;

    public NotificationHandler(Context context) {
        this.context = context;
//        notificationManager = NotificationManagerCompat.from(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void createNotification(Boolean isDriving, String title, String content, Boolean isPersistentNotification){
        int smallIcon;
        if (isDriving)
            smallIcon = R.drawable.ic_logo_white;
        else
            smallIcon = R.mipmap.ic_kb_check;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(smallIcon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(content)
                .setOngoing(isPersistentNotification)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        Intent intent;
        if(isDriving){
            intent = new Intent(context, PassengerActivity.class);
        } else{
            intent = new Intent(context, SeeDrivingScoreActivity.class);
            notificationBuilder.setAutoCancel(true);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
