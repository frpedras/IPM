package iscte.ipm.ipm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class NotificationBarAlarm extends BroadcastReceiver {

    NotificationManager notifyManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("NotificationAlarm", "onReceive");

        notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// This Activity will be started when the user clicks the notification
// in the notification bar
        Intent notificationIntent = new Intent(context, CreatingAlert.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Notification notif = new Notification(R.drawable.key, "A new notification just popped in!", System.currentTimeMillis());

// Play sound?
// If you want you can play a sound when the notification shows up.
// Place the MP3 file into the /raw folder.
        //notif.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.jingle);

        notif.setLatestEventInfo(context, "Notification Title", "Notification Text", contentIntent);

        notifyManager.notify(1, notif);
    }
}