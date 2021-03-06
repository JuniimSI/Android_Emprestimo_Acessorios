package com.example.juniorf.mylastaplicationandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = "Local Próximo";
        String text = intent.getStringExtra("link");

        long[] vibrate = {300, 600, 300, 600};

        Intent i = new Intent(context, MapsActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setAutoCancel(true);

        builder.setContentTitle(title);
        builder.setContentText(text);

        builder.setContentIntent(pi);
        builder.setSmallIcon(R.drawable.fundofama);


        builder.setVibrate(vibrate);

        // No AndroidManifest, o minSdkVersion deve
        // ser '11', para usar o getNotification()
        Notification nf = builder.getNotification();
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int id = 2014;
        nm.notify(id, nf);

    }
}